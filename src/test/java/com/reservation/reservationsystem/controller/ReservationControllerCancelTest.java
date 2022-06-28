package com.reservation.reservationsystem.controller;

import com.reservation.reservationsystem.config.QuerydslConfiguration;
import com.reservation.reservationsystem.entity.contstants.PaymentStatus;
import com.reservation.reservationsystem.entity.contstants.PaymentType;
import com.reservation.reservationsystem.entity.contstants.ReservationStatus;
import com.reservation.reservationsystem.entity.reservation.Reservation;
import com.reservation.reservationsystem.exception.ErrorCode;
import com.reservation.reservationsystem.exception.ReservationException;
import com.reservation.reservationsystem.service.reservation.ReservationService;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("local")
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QuerydslConfiguration.class)
@DataJpaTest
class ReservationControllerCancelTest {

    @InjectMocks
    private ReservationController controller;

    @Mock
    private ReservationService service;

    @Autowired
    private TestEntityManager em;

    private MockMvc mockMvc;

    private Reservation reservation;

    private Reservation reservation2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new GlobalExceptionHandler()).build();

        reservation = Reservation.of(
                ReservationStatus.PENDING,
                100000L,
                LocalDate.of(2022, 01, 16),
                LocalTime.of(13, 00, 00),
                3
        );
        em.persist(reservation);

        reservation2 = Reservation.of(
                ReservationStatus.CANCELED,
                100000L,
                LocalDate.of(2022, 01, 16),
                LocalTime.of(13, 00, 00),
                3
        );
        em.persist(reservation2);
    }

    @Test
    @DisplayName("정상 취소 처리")
    public void test() throws Exception {
        // Given
        Long reservationId = reservation.getId();

        // When & Then
        mockMvc.perform(
                put("/business/stores/reservations/{reservationId}", reservationId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("예약취소 조건 불충족으로 exception 발생")
    public void testWithException() throws Exception {
        // Given
        long reservationId = reservation2.getId();

        willThrow(new ReservationException(ErrorCode.NOT_POSSIBLE_CANCEL_RESERVATION))
                .given(service).cancel(reservationId);

        // When & Then
        mockMvc.perform(
                put("/business/stores/reservations/{reservationId}", reservationId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }
}
