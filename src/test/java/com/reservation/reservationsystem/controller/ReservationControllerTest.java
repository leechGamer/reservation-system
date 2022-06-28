package com.reservation.reservationsystem.controller;

import com.reservation.reservationsystem.dto.reservation.ReservationSearchRequestDTO;
import com.reservation.reservationsystem.entity.contstants.PaymentStatus;
import com.reservation.reservationsystem.entity.contstants.PaymentType;
import com.reservation.reservationsystem.entity.contstants.ReservationStatus;
import com.reservation.reservationsystem.entity.reservation.Reservation;
import com.reservation.reservationsystem.service.reservation.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {

    @InjectMocks
    private ReservationController controller;

    @Mock
    private ReservationService service;

    private MockMvc mockMvc;

    private Reservation reservation;

    private Reservation reservation2;

    private List<Reservation> reservations;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        reservation = Reservation.of(
                ReservationStatus.PENDING,
                100000L,
                LocalDate.of(2022, 01, 16),
                LocalTime.of(13, 00, 00),
                3
        );

        reservation2 = Reservation.of(
                ReservationStatus.PENDING,
                30000L,
                LocalDate.of(2022, 01, 10),
                LocalTime.of(13, 00, 00),
                5
        );

        reservations = new ArrayList<>();
        reservations.add(reservation);
        reservations.add(reservation2);
    }

    @Test
    public void test() throws Exception {
        // Given
        ReservationSearchRequestDTO request = ReservationSearchRequestDTO.builder()
                .start(LocalDate.of(2022, 01, 03))
                .end(LocalDate.of(2022, 02, 03))
                .status(ReservationStatus.PENDING)
                .build();

        long storeId = 4L;

        given(service.search(anyLong(), any(ReservationSearchRequestDTO.class))).willReturn(reservations);

        // When & Then
        ResultActions result = mockMvc.perform(
                get("/business/stores/reservations/{storeId}", storeId)
                        .param("request", request.toString())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
}
