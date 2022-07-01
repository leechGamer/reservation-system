package com.reservation.reservationsystem.service.menu;

import com.reservation.reservationsystem.config.QuerydslConfiguration;
import com.reservation.reservationsystem.dto.menu.MenuSaveRequestDTO;
import com.reservation.reservationsystem.entity.company.Company;
import com.reservation.reservationsystem.entity.contstants.StoreCategory;
import com.reservation.reservationsystem.entity.menu.Menu;
import com.reservation.reservationsystem.entity.store.Address;
import com.reservation.reservationsystem.entity.store.Location;
import com.reservation.reservationsystem.entity.store.Store;
import com.reservation.reservationsystem.exception.DuplicateEntityException;
import com.reservation.reservationsystem.repository.menu.MenuRepository;
import com.reservation.reservationsystem.repository.store.StoreRepository;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QuerydslConfiguration.class)
@DataJpaTest
class MenuServiceTest {

    @InjectMocks
    MenuService menuService;

    @Autowired
    private TestEntityManager entityManager;

    @Mock
    private MenuRepository repository;

    @Mock
    private StoreRepository storeRepository;

    private Store store;

    private Company company;

    private Menu menu;

    private Menu menu2;

    private MenuSaveRequestDTO menuDto;

    @BeforeEach
    public void setUp () {
        String businessNumber = "1234555545";
        String name = "companyName";
        String companyPhoneNumber = "01099999999";
        company = Company.of(businessNumber, name, companyPhoneNumber);

        Location location = Location.of(12.234, 34.333);
        Address address = Address.of("서울시", "123-222");
        String phoneNumber = "01012341234";
        store = Store.of(
                "store1",
                StoreCategory.RESTAURANT,
                "description",
                location,
                address,
                phoneNumber
        );
        company.addStore(store);
        entityManager.persist(store);
        entityManager.persist(company);

        menu = Menu.of(
                "메뉴1",
                null,
                "메뉴 설명",
                20000L
        );

        menuDto = new MenuSaveRequestDTO(
                menu.getName(),
                menu.getAmount(),
                menu.getCategory(),
                menu.getDescription()
        );
        menu2 = Menu.of(
                "메뉴1",
                null,
                "메뉴 설명",
                20000L
        );
    }

    @Test
    @DisplayName("menu를 등록 - success")
    public void test() {
        // Given
        MenuSaveRequestDTO menuDto = new MenuSaveRequestDTO(
                menu.getName(),
                menu.getAmount(),
                menu.getCategory(),
                menu.getDescription()
        );
        given(storeRepository.findById(store.getId())).willReturn(Optional.of(store));

        // When
        menuService.save(store.getId(), menuDto);

        // Then
        verify(repository, times(1)).save(any(Menu.class));
    }

    @Test
    @DisplayName("menu를 등록시 store가 null - NullPointerException")
    public void testWithStoreIsNull() {
        // Given
        Menu menu = Menu.of(
                "메뉴1",
                null,
                "메뉴 설명",
                20000L
        );

        MenuSaveRequestDTO menuDto = new MenuSaveRequestDTO(
                menu.getName(),
                menu.getAmount(),
                menu.getCategory(),
                menu.getDescription()
        );
        given(storeRepository.findById(10L)).willReturn(null);

        // When & Then
        Assertions.assertThrows(NullPointerException.class, () -> {
            menuService.save(10L, menuDto);
        });
    }

    @Test
    @DisplayName("중복된 메뉴는 추가할 수 없다.")
    public void testWithDuplicate() {
        // Given
        store.addMenu(menu);
        MenuSaveRequestDTO menuDto2 =  new MenuSaveRequestDTO(
                menu2.getName(),
                menu2.getAmount(),
                menu2.getCategory(),
                menu2.getDescription()
        );
        given(storeRepository.findById(store.getId())).willReturn(Optional.of(store));

        // When & Then
        Assertions.assertThrows(DuplicateEntityException.class, () -> {
            menuService.save(store.getId(), menuDto2);
        });
    }
}
