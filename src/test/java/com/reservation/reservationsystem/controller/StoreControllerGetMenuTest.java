package com.reservation.reservationsystem.controller;

import com.reservation.reservationsystem.config.QuerydslConfiguration;
import com.reservation.reservationsystem.entity.company.Company;
import com.reservation.reservationsystem.entity.contstants.CategoryType;
import com.reservation.reservationsystem.entity.contstants.StoreCategory;
import com.reservation.reservationsystem.entity.menu.Menu;
import com.reservation.reservationsystem.entity.store.Address;
import com.reservation.reservationsystem.entity.store.Location;
import com.reservation.reservationsystem.entity.store.Store;
import com.reservation.reservationsystem.exception.ErrorCode;
import com.reservation.reservationsystem.exception.NotFoundEntityException;
import com.reservation.reservationsystem.service.store.StoreService;
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

import java.util.HashSet;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QuerydslConfiguration.class)
@DataJpaTest
class StoreControllerGetMenuTest {

    @InjectMocks
    private StoreController storeController;

    @Mock
    private StoreService storeService;

    private MockMvc mockMvc;

    @Autowired
    private TestEntityManager em;

    private Company company;

    private Store store;

    private HashSet<Menu> menus;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(storeController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

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

        Menu menu = Menu.of(
                "버섯크림리조또",
                CategoryType.FOOD,
                "트러플향이 가득한 버섯크림리조또",
                18500L
        );

        Menu menu2 = Menu.of(
                "가지비프토마토리조또",
                CategoryType.FOOD,
                "상큼한 가지비프토마토리조또",
                17500L
        );

        company.addStore(store);
        em.persist(company);

        store.addMenu(menu);
        store.addMenu(menu2);

        menus = new HashSet<>();
        menus.add(menu);
        menus.add(menu2);
    }

    @Test
    @DisplayName("해당 store의 menu 검색")
    public void test() throws Exception {

        // Given
        given(storeService.getMenu(store.getId())).willReturn(menus);

        // When & Then
        mockMvc.perform(
                get("/stores/{storeId}/menu", store.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    @DisplayName("store 정보가 없을 경우 exception 발생")
    public void testWithException() throws Exception {
        // Given
        long storeId = 3L;

        willThrow(new NotFoundEntityException(ErrorCode.NOT_FOUND_ENTITY))
                .given(storeService).getMenu(storeId);

        // When & Then
        mockMvc.perform(
                get("/stores/{storeId}/menu", storeId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }
}
