package com.reservation.reservationsystem.service.reservation;

import com.reservation.reservationsystem.config.QuerydslConfiguration;
import com.reservation.reservationsystem.dto.reservation.ReservationSearchRequest;
import com.reservation.reservationsystem.entity.company.Company;
import com.reservation.reservationsystem.entity.contstants.PaymentStatus;
import com.reservation.reservationsystem.entity.contstants.PaymentType;
import com.reservation.reservationsystem.entity.contstants.ReservationStatus;
import com.reservation.reservationsystem.entity.contstants.StoreCategory;
import com.reservation.reservationsystem.entity.customer.Customer;
import com.reservation.reservationsystem.entity.reservation.Reservation;
import com.reservation.reservationsystem.entity.store.Address;
import com.reservation.reservationsystem.entity.store.Location;
import com.reservation.reservationsystem.entity.store.Store;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ActiveProfiles("local")
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QuerydslConfiguration.class)
@DataJpaTest
class ReservationServiceSearchTest {

    @InjectMocks
    private ReservationService reservationService;

    @Autowired
    private TestEntityManager em;

    @Mock
    private ReservationRepository reservationRepository;

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
        em.persist(store);

        customer = Customer.of(
                "lee",
                "lee@naver.com",
                "01021112222",
                new BCryptPasswordEncoder().encode("1234")
        );
        em.persist(customer);

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
                ReservationStatus.PENDING,
                100000L,
                PaymentStatus.READY,
                PaymentType.NONE,
                null,
                LocalDate.of(2022, 01, 10),
                LocalTime.of(13, 00, 00),
                3
        );

        em.persist(reservation);
        em.persist(reservation2);

        reservation.setStore(store);
        reservation2.setStore(store);
    }

    /**
     * reservationRepository의 결과값을 반환한다.
     */
    @Test
    @DisplayName("reservation 조회")
    public void test() {
        // Given
        ReservationSearchRequest request = new ReservationSearchRequest(
                ReservationStatus.PENDING,
                LocalDate.of(2022, 01, 03),
                LocalDate.of(2022, 02, 03)
        );

        List<Reservation> expected = new ArrayList<>();
        expected.add(reservation);
        expected.add(reservation2);

        when(reservationRepository.search(store.getId(), request)).thenReturn(expected);

        // When
        List<Reservation> actual = reservationService.search(store.getId(), request);

        // Then
        Assertions.assertEquals(2, actual.size());
        Assertions.assertTrue(actual.contains(reservation));
        Assertions.assertTrue(actual.contains(reservation2));
    }
}
