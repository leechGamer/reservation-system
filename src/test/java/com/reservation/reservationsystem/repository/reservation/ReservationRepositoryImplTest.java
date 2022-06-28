package com.reservation.reservationsystem.repository.reservation;

import com.reservation.reservationsystem.config.QuerydslConfiguration;
import com.reservation.reservationsystem.dto.reservation.ReservationSearchRequestDTO;
import com.reservation.reservationsystem.entity.company.Company;
import com.reservation.reservationsystem.entity.contstants.PaymentStatus;
import com.reservation.reservationsystem.entity.contstants.PaymentType;
import com.reservation.reservationsystem.entity.contstants.ReservationStatus;
import com.reservation.reservationsystem.entity.contstants.StoreCategory;
import com.reservation.reservationsystem.entity.customer.Customer;
import com.reservation.reservationsystem.entity.customer.UserRoleType;
import com.reservation.reservationsystem.entity.reservation.Reservation;
import com.reservation.reservationsystem.entity.store.Address;
import com.reservation.reservationsystem.entity.store.Location;
import com.reservation.reservationsystem.entity.store.Store;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
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
import java.util.List;

@ActiveProfiles("local")
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Import(QuerydslConfiguration.class)
class ReservationRepositoryImplTest {

    @Autowired
    private ReservationRepositoryImpl repositoryImpl;

    @Autowired
    private TestEntityManager em;

    private Company company;

    private Store store;

    private Customer customer;

    private Reservation r;

    private Reservation r2;

    private Reservation r3;

    private Reservation r4;

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
        em.persist(company);
        store.setCompany(company);
        em.persist(store);

        customer = Customer.of(
                "lee",
                "lee@naver.com",
                "01021112222",
                new BCryptPasswordEncoder().encode("1234"),
                UserRoleType.NORMAL.toString()
        );
        em.persist(customer);

        r = Reservation.of(
                ReservationStatus.PENDING,
                0,
                LocalDate.of(2022, 01, 05),
                LocalTime.of(13, 00),
                2
        );

        r2 = Reservation.of(
                ReservationStatus.PENDING,
                10000,
                LocalDate.of(2022, 01, 15),
                LocalTime.of(14, 00),
                2
        );

        r3 = Reservation.of(
                ReservationStatus.EXPIRED,
                10000,
                LocalDate.of(2022, 02, 25),
                LocalTime.of(14, 00),
                2
        );

        r4 = Reservation.of(
                ReservationStatus.CANCELED,
                10000,
                LocalDate.of(2022, 03, 05),
                LocalTime.of(14, 00),
                2
        );

        customer.addReservation(r);
        customer.addReservation(r2);
        customer.addReservation(r3);
        customer.addReservation(r4);

        r.setCustomer(customer);
        r2.setCustomer(customer);
        r3.setCustomer(customer);
        r4.setCustomer(customer);

        r.setStore(store);
        r2.setStore(store);
        r3.setStore(store);
        r4.setStore(store);

        em.persist(r);
        em.persist(r2);
        em.persist(r3);
        em.persist(r4);
    }

    /**
     * reservationList : r, r2, r3, r4 (총 4 개)
     * 검색 옵션: 예약상태(pending), 날짜(2022-01-04 ~ 2022-03-30)
     * 검색결과: r, r2  (2개의 예약건)
     */
    @Test
    @DisplayName("reservation 조회 : 예약상태 & 날짜")
    public void test() {
        // Given
        ReservationSearchRequestDTO request = ReservationSearchRequestDTO.builder()
                .status(ReservationStatus.PENDING)
                .start(LocalDate.of(2022, 01, 04))
                .end(LocalDate.of(2022, 03, 30))
                .build();

        // When
        List<Reservation> reservations = repositoryImpl.search(store.getId(), request);

        // Then
        Assertions.assertEquals(2, reservations.size());
        Assertions.assertTrue(reservations.contains(r));
        Assertions.assertTrue(reservations.contains(r2));
        Assertions.assertFalse(reservations.contains(r3));
        Assertions.assertFalse(reservations.contains(r4));
    }

    /**
     * reservationList : r, r2, r3, r4 (총 4 개)
     * 검색 옵션: 날짜 (2022-02-25 ~ 2022-03-30)
     * 검색결과: r3, r4  (2개의 예약건)
     */
    @Test
    @DisplayName("reservation 조회: 날짜")
    public void testWithStatusIsNull() {
        // Given
        ReservationSearchRequestDTO request = ReservationSearchRequestDTO.builder()
                .start(LocalDate.of(2022, 02, 25))
                .end(LocalDate.of(2022, 03, 30))
                .build();

        // When
        List<Reservation> reservations = repositoryImpl.search(store.getId(), request);

        // Then
        Assertions.assertEquals(2, reservations.size());
        Assertions.assertFalse(reservations.contains(r));
        Assertions.assertFalse(reservations.contains(r2));
        Assertions.assertTrue(reservations.contains(r3));
        Assertions.assertTrue(reservations.contains(r4));
    }

    /**
     * reservationList : r, r2, r3, r4 (총 4 개)
     * 검색 옵션: 상태 (pending)
     * 검색결과: r, r2  (2개의 예약건)
     */
    @Test
    @DisplayName("reservation 조회: 예약 상태로 예약건들을 조회할 수 있다.")
    public void testWithDateIsNull() {
        // Given
        ReservationSearchRequestDTO request = ReservationSearchRequestDTO.builder()
                .status(ReservationStatus.PENDING)
                .build();

        // When
        List<Reservation> reservations = repositoryImpl.search(store.getId(), request);

        // Then
        Assertions.assertEquals(2, reservations.size());
        Assertions.assertTrue(reservations.contains(r));
        Assertions.assertTrue(reservations.contains(r2));
        Assertions.assertFalse(reservations.contains(r3));
        Assertions.assertFalse(reservations.contains(r4));
    }
}
