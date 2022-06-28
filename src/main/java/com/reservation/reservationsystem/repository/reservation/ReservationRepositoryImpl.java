package com.reservation.reservationsystem.repository.reservation;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.reservation.reservationsystem.dto.reservation.ReservationSearchRequestDTO;
import com.reservation.reservationsystem.entity.contstants.ReservationStatus;
import com.reservation.reservationsystem.entity.reservation.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.reservation.reservationsystem.entity.reservation.QReservation.reservation;
import static com.reservation.reservationsystem.entity.store.QStore.store;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<Reservation> search(Long storeId, ReservationSearchRequestDTO request) {

        BooleanBuilder builder = new BooleanBuilder();

        ReservationStatus status = request.getStatus();

        LocalDate start = request.getStart();

        LocalDate end = request.getEnd();

        if (status != null) {
            builder.and(reservation.reservationStatus.eq(status));
        }

        if (start != null && end != null) {
            builder.and(reservation.reservedDate.between(start, end));
        }

         return queryFactory.selectFrom(reservation)
                .join(store)
                .on(store.id.eq(storeId))
                .where(builder)
                .fetch();
    }
}
