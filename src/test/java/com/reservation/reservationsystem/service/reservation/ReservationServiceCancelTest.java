package com.reservation.reservationsystem.service.reservation;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.reservation.reservationsystem.config.QuerydslConfiguration;
import com.reservation.reservationsystem.entity.company.Company;
import com.reservation.reservationsystem.entity.contstants.ReservationStatus;
import com.reservation.reservationsystem.entity.contstants.StoreCategory;
import com.reservation.reservationsystem.entity.customer.Customer;
import com.reservation.reservationsystem.entity.customer.UserRoleType;
import com.reservation.reservationsystem.entity.reservation.Reservation;
import com.reservation.reservationsystem.entity.store.Address;
import com.reservation.reservationsystem.entity.store.Location;
import com.reservation.reservationsystem.entity.store.Store;
import com.reservation.reservationsystem.exception.ReservationException;
import com.reservation.reservationsystem.repository.reservation.ReservationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QuerydslConfiguration.class)
@DataJpaTest
class ReservationServiceCancelTest {

    @InjectMocks
    private ReservationService reservationService;

    @Autowired
    private TestEntityManager em;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private JPAQueryFactory queryFactory;

    private Store store;

    private Customer customer;

    private Company company;

    private Reservation reservation;

    private Reservation reservation2;

    @BeforeEach
    public void setUp() {
        company = Company.of(
                "1234567890",
                "company1",
                "07023329987"
        );
        store = Store.of(
                "store1",
                StoreCategory.RESTAURANT,
                "description",
                Location.of(12.989, 22.003),
                Address.of("seoul", "123-89"),
                "02-0900-0009"
        );

        company.addStore(store);
        store.setCompany(company);
        em.persist(company);

        customer = Customer.of(
                "lee",
                "lee@naver.com",
                "01021112222",
                new BCryptPasswordEncoder().encode("1234"),
                UserRoleType.NORMAL.toString()
        );
        em.persist(customer);

        reservation = Reservation.of(
                ReservationStatus.PENDING,
                100000L,
                LocalDate.now().plusDays(1),
                LocalTime.now(),
                3
        );
        em.persist(reservation);
        reservation.setStore(store);

        reservation2 = Reservation.of(
                ReservationStatus.PENDING,
                100000L,
                LocalDate.now(),
                LocalTime.now(),
                3
        );
        em.persist(reservation2);
        reservation2.setStore(store);
    }

    /**
     * 예약 취소 시간이 예약일 5시간 이내이면 취소가 불가능하다.
     */
    @Test
    @DisplayName("예약취소 - 예약취소가 불가능한 예약건")
    public void test() {
        // Given
        // todo:: power mockito 적용해서 now 시간 조정하기
        when(reservationRepository.findById(reservation2.getId())).thenReturn(Optional.of(reservation2));

        // When & Then
        Assertions.assertThrows(
                ReservationException.class, () -> {
                    reservationService.cancel(reservation2.getId());
                });
    }

    /**
     * 예약 당일 5시간 전까지 예약 취소가 가능하다.
     */
    @Test
    @DisplayName("예약취소 - 예약취소가 가능한 예약건")
    public void testWithReservation() {
        // Given
        // todo:: power mockito 적용해서 now 시간 조정하기
        when(reservationRepository.findById(reservation.getId())).thenReturn(Optional.of(reservation));

        // When
        reservationService.cancel(reservation.getId());

        // Then
        Reservation actual = reservationRepository.findById(reservation.getId()).get();
        Assertions.assertEquals(ReservationStatus.CANCELED, actual.getReservationStatus());
    }
}
