package com.reservation.reservationsystem.service.store;

import com.reservation.reservationsystem.config.QuerydslConfiguration;
import com.reservation.reservationsystem.entity.company.Company;
import com.reservation.reservationsystem.entity.contstants.StoreCategory;
import com.reservation.reservationsystem.entity.store.Address;
import com.reservation.reservationsystem.entity.store.Location;
import com.reservation.reservationsystem.entity.store.Store;
import com.reservation.reservationsystem.exception.NotFoundEntityException;
import com.reservation.reservationsystem.repository.store.StoreRepository;
import javassist.NotFoundException;
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
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QuerydslConfiguration.class)
@DataJpaTest
class StoreServiceGetDetailTest {

    @InjectMocks
    private StoreService storeService;

    @Autowired
    private TestEntityManager em;

    @Mock
    StoreRepository storeRepository;

    private Store store;

    private Company company;

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
        em.persist(store);
    }

    @Test
    @DisplayName("store 정보를 가져온다")
    public void test() throws NotFoundException {
        // Given
        given(storeRepository.findById(store.getId())).willReturn(Optional.of(store));

        // When
        Store actual = storeService.getDetail(store.getId());

        // Then
        Assertions.assertEquals(store.getId(), actual.getId());
    }

    @Test
    @DisplayName("store 정보가 없을 경우 exception 발생")
    public void testWithException() {
        // Given
        given(storeRepository.findById(3L)).willReturn(Optional.empty());

        // When & Then
        Assertions.assertThrows(
                NotFoundEntityException.class, () -> {
                    storeService.getDetail(store.getId());
                });
    }
}
