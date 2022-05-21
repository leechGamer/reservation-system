package com.reservation.reservationsystem.repository.menu;

import com.reservation.reservationsystem.config.QuerydslConfiguration;
import com.reservation.reservationsystem.entity.company.Company;
import com.reservation.reservationsystem.entity.contstants.StoreCategory;
import com.reservation.reservationsystem.entity.menu.Menu;
import com.reservation.reservationsystem.entity.store.Address;
import com.reservation.reservationsystem.entity.store.Location;
import com.reservation.reservationsystem.entity.store.Store;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("local")
@ExtendWith(SpringExtension.class)
@Import(QuerydslConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class MenuRepositoryTest {

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    private TestEntityManager entityManager;

    @DisplayName("menu를 등록 - success")
    @Test
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
        company.addStore(store);

        Menu menu = Menu.of(
                "메뉴1",
                null,
                "메뉴 설명",
                20000L
        );
        store.addMenu(menu);

        entityManager.persist(company);
        store.setCompany(company);
        entityManager.persist(store);

        // When
        Menu actual = menuRepository.save(menu);

        // Then
        Assertions.assertEquals(menu.getName(), actual.getName());
        Assertions.assertEquals(menu.getCategory(), actual.getCategory());
        Assertions.assertEquals(menu.getDescription(), actual.getDescription());
        Assertions.assertEquals(menu.getAmount(), actual.getAmount());
    }
}
