package com.reservation.reservationsystem.service;

import com.reservation.reservationsystem.dto.menu.MenuSaveRequestDTO;
import com.reservation.reservationsystem.entity.company.Company;
import com.reservation.reservationsystem.entity.contstants.StoreCategory;
import com.reservation.reservationsystem.entity.menu.Menu;
import com.reservation.reservationsystem.entity.store.Address;
import com.reservation.reservationsystem.entity.store.Location;
import com.reservation.reservationsystem.entity.store.Store;
import com.reservation.reservationsystem.repository.menu.MenuRepository;
import com.reservation.reservationsystem.repository.store.StoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ActiveProfiles("local")
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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

    @BeforeEach
    public void init () {

    }
    @Test
    @DisplayName("menu를 등록 - success")
    public void test() {
        // Given
        String businessNumber = "1234555545";
        String name = "companyName";
        String companyPhoneNumber = "01099999999";
        Company company = Company.of(businessNumber, name, companyPhoneNumber);

        Location location = Location.of(12.234, 34.333);
        Address address = Address.of("서울시", "123-222");
        String phoneNumber = "01012341234";
        Store store = Store.of(
                "store1",
                StoreCategory.RESTAURANT,
                "description",
                location,
                address,
                phoneNumber
        );
        entityManager.persist(store);
        company.addStore(store);

        Menu menu = Menu.of(
                "메뉴1",
                null,
                "메뉴 설명",
                20000L
        );
        store.addMenu(menu);

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
}
