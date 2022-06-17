package com.reservation.reservationsystem.controller;

import com.google.gson.Gson;
import com.reservation.reservationsystem.config.QuerydslConfiguration;
import com.reservation.reservationsystem.dto.store.StoreSaveDTO;
import com.reservation.reservationsystem.entity.company.Company;
import com.reservation.reservationsystem.entity.contstants.StoreCategory;
import com.reservation.reservationsystem.service.store.StoreService;
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
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@Import(QuerydslConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StoreControllerSaveTest {

    @InjectMocks
    private StoreController storeController;

    @Mock
    private StoreService storeService;

    private MockMvc mockMvc;

    private Gson gson;

    private Company company;

    @Autowired
    private TestEntityManager em;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(storeController).build();
        gson = new Gson();
        company = Company.of(
                "12341234",
                "companyName",
                "0701231233"
        );
        em.persist(company);
    }

    @Test
    @DisplayName("Store 등록")
    public void test() throws Exception {
        // Given
        StoreSaveDTO storeSaveDTO = new StoreSaveDTO(
                "가게1",
                StoreCategory.RESTAURANT,
                "description",
                "서울시",
                "122-123",
                36.1234,
                13.1234,
                "01012341234"
        );

        // When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.post("/companies/{companyId}/stores", company.getId())
                        .content(gson.toJson(storeSaveDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    @DisplayName("Store 등록 - invalid param")
    public void testWithInvalidParam() throws Exception {
        // Given
        StoreSaveDTO storeSaveDTO = StoreSaveDTO.builder()
                .name("store1")
                .latitude(123.123)
                .build();

        // When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.post("/companies/{companyId}/stores", company.getId())
                        .content(gson.toJson(storeSaveDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is4xxClientError());
    }
}
