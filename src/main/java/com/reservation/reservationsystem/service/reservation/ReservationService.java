package com.reservation.reservationsystem.service.reservation;

import com.reservation.reservationsystem.dto.reservation.ReservationRequestDTO;
import com.reservation.reservationsystem.dto.reservation.ReservationSearchRequestDTO;
import com.reservation.reservationsystem.entity.contstants.ReservationStatus;
import com.reservation.reservationsystem.entity.customer.Customer;
import com.reservation.reservationsystem.entity.reservation.Reservation;
import com.reservation.reservationsystem.entity.reservation.ReservationMenu;
import com.reservation.reservationsystem.entity.store.Store;
import com.reservation.reservationsystem.exception.ErrorCode;
import com.reservation.reservationsystem.exception.NotFoundEntityException;
import com.reservation.reservationsystem.exception.ReservationException;
import com.reservation.reservationsystem.repository.customer.CustomerRepository;
import com.reservation.reservationsystem.repository.menu.MenuRepository;
import com.reservation.reservationsystem.repository.reservation.ReservationMenuRepository;
import com.reservation.reservationsystem.repository.reservation.ReservationRepository;
import com.reservation.reservationsystem.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    final static int AVAILABLE_CANCEL_HOUR = 5;

    final private ReservationRepository reservationRepository;

    final private CustomerRepository customerRepository;

    final private StoreRepository storeRepository;

    final private MenuRepository menuRepository;

    final private ReservationMenuRepository reservationMenuRepository;


    public List<Reservation> search(Long storeId, ReservationSearchRequestDTO requestDTO) {
        List<Reservation> reservations = reservationRepository.search(storeId, requestDTO);
        return reservations;
    }

    @Transactional
    public void cancel(Long reservationId) throws ReservationException {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        Optional.ofNullable(reservation)
                .filter(r -> this.checkAvailableTime(r.get()))
                .filter(r -> this.checkStatus(r.get()))
                .ifPresentOrElse(
                        r -> {
                            r.get().setReservationStatus(ReservationStatus.CANCELED);
                            r.get().setDeletedAt(LocalDateTime.now());
                        },
                        () -> {
                            throw new ReservationException(ErrorCode.NOT_POSSIBLE_CANCEL_RESERVATION);
                        }
                );
    }
    public List<Reservation> getList(User user) {
        String email = user.getUsername();
        Customer customer = customerRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(ErrorCode.EMAIL_NOT_FOUND.getMessage())
        );
        return customer.getReservations();
    }

    @Transactional
    public Reservation reserve(User user, ReservationRequestDTO requestDTO) throws ReservationException {
        Store store = Optional.ofNullable(storeRepository.getById(requestDTO.getStoreId())).orElseThrow(
                () -> {
                    throw new NotFoundEntityException(ErrorCode.NOT_FOUND_ENTITY);
                }
        );
        String email = user.getUsername();
        Customer customer = customerRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(ErrorCode.EMAIL_NOT_FOUND.getMessage())
        );

        LocalDateTime localDateTime = requestDTO.getReservedDateTime();
        LocalDate reservedDate = localDateTime.toLocalDate();
        LocalTime reservedTime = localDateTime.toLocalTime();
        Reservation reservation = Reservation.of(
                ReservationStatus.PENDING,
                requestDTO.getAmount(),
                reservedDate,
                reservedTime,
                requestDTO.getNumberOfCustomer()
        );
        reservationRepository.save(reservation);

        Optional.ofNullable(menuRepository.getById(requestDTO.getMenuId())).ifPresentOrElse(
                m -> {
                    ReservationMenu reservationMenu = ReservationMenu.of(reservation, m);
                    reservationMenuRepository.save(reservationMenu);
                },
                () -> {
                    throw new NotFoundEntityException(ErrorCode.NOT_FOUND_ENTITY);
                }
        );
        reservation.setStore(store);
        reservation.setCustomer(customer);

        return reservation;
    }

    private boolean checkAvailableTime(Reservation reservation) {
        LocalDate date = reservation.getReservedDate();
        LocalTime time = reservation.getReservedTime();
        LocalDateTime reservedDateTime = LocalDateTime.of(date, time);

        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(reservedDateTime.minusHours(AVAILABLE_CANCEL_HOUR))
                || now.equals(reservedDateTime.plusHours(AVAILABLE_CANCEL_HOUR))
        ) {
            return false;
        }
        return true;
    }

    private boolean checkStatus(Reservation reservation) {
        ReservationStatus status = reservation.getReservationStatus();
        if (status.equals(ReservationStatus.EXPIRED)
                || status.equals(ReservationStatus.CANCELED)
        ) {
            return false;
        }
        return true;
    }
}

