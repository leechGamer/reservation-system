package com.reservation.reservationsystem.service.store;

import com.reservation.reservationsystem.config.QuerydslConfiguration;
import com.reservation.reservationsystem.entity.company.Company;
import com.reservation.reservationsystem.entity.contstants.CategoryType;
import com.reservation.reservationsystem.entity.contstants.StoreCategory;
import com.reservation.reservationsystem.entity.menu.Menu;
import com.reservation.reservationsystem.entity.store.Address;
import com.reservation.reservationsystem.entity.store.Location;
import com.reservation.reservationsystem.entity.store.Store;
import com.reservation.reservationsystem.exception.NotFoundEntityException;
import com.reservation.reservationsystem.repository.store.StoreRepository;
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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.BDDMockito.given;

@ActiveProfiles("local")
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QuerydslConfiguration.class)
@DataJpaTest
class StoreServiceGetMenuTest {

    @InjectMocks
    private StoreService storeService;

    @Autowired
    private TestEntityManager em;

    @Mock
    StoreRepository storeRepository;

    private Store store;

    private Company company;

    private HashSet<Menu> menus;

    private Menu menu;

    private Menu menu2;

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

        menu = Menu.of(
                "버섯크림리조또",
                CategoryType.FOOD,
                "트러플향이 가득한 버섯크림리조또",
                18500L
        );

        menu2 = Menu.of(
                "가지비프토마토리조또",
                CategoryType.FOOD,
                "상큼한 가지비프토마토리조또",
                17500L
        );

        company.addStore(store);
        em.persist(company);
        em.persist(store);

        store.addMenu(menu);
        store.addMenu(menu2);

        menus = new HashSet<>();
        menus.add(menu);
        menus.add(menu2);
    }

    @Test
    @DisplayName("store의 menu를 가져온다")
    public void test() throws NotFoundEntityException {
        // Given
        given(storeRepository.findById(store.getId())).willReturn(Optional.of(store));

        // When
        Set<Menu> actual = storeService.getMenu(store.getId());

        // Then
        Assertions.assertTrue(actual.contains(menu));
        Assertions.assertTrue(actual.contains(menu2));
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
