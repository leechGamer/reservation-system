package com.reservation.reservationsystem.controller;

import com.reservation.reservationsystem.config.QuerydslConfiguration;
import com.reservation.reservationsystem.entity.contstants.PaymentStatus;
import com.reservation.reservationsystem.entity.contstants.PaymentType;
import com.reservation.reservationsystem.entity.contstants.ReservationStatus;
import com.reservation.reservationsystem.entity.reservation.Reservation;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("local")
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QuerydslConfiguration.class)
@DataJpaTest
class ReservationControllerGetDetailTest {

    @InjectMocks
    private ReservationController controller;

    @Mock
    private ReservationService service;

    @Autowired
    private TestEntityManager em;

    private MockMvc mockMvc;

    private Reservation reservation;

    private Reservation reservation2;

    private Reservation reservation3;

    private Reservation target;

    private Long targetId;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        reservation = Reservation.of(
                ReservationStatus.PENDING,
                100000L,
                PaymentStatus.READY,
                PaymentType.NONE,
                null,
                LocalDate.of(2022, 01, 16),
                LocalTime.of(13, 00, 00),
                3
        );

        reservation2 = Reservation.of(
                ReservationStatus.CANCELED,
                100000L,
                PaymentStatus.READY,
                PaymentType.NONE,
                null,
                LocalDate.of(2022, 01, 16),
                LocalTime.of(13, 00, 00),
                3
        );

        reservation3 = Reservation.of(
                ReservationStatus.CANCELED,
                100000L,
                PaymentStatus.READY,
                PaymentType.NONE,
                null,
                LocalDate.of(2022, 01, 16),
                LocalTime.of(13, 00, 00),
                3
        );

        target = Reservation.of(
                ReservationStatus.CANCELED,
                100000L,
                PaymentStatus.READY,
                PaymentType.NONE,
                null,
                LocalDate.of(2022, 01, 16),
                LocalTime.of(13, 00, 00),
                3
        );
        em.persist(reservation);
        em.persist(reservation2);
        em.persist(reservation3);
        em.persist(target);

    }

    @Test
    @DisplayName("reservation 상세 확인")
    public void test() throws Exception {
        // Given
        targetId = target.getId();

        // When & Then
        mockMvc.perform(
                get("/reservations/{reservationId}", targetId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
}