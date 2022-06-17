package com.reservation.reservationsystem.controller;

import com.google.gson.Gson;
import com.reservation.reservationsystem.dto.company.CompanyAddDTO;
import com.reservation.reservationsystem.service.company.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class CompanyControllerAddTest {

    @InjectMocks
    private CompanyController controller;

    @Mock
    private CompanyService companyService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("회사 등록")
    public void test() throws Exception {
        // Given
        String businessNumber = "1234455";
        String name = "co";
        String phone = "01021231234";
        CompanyAddDTO dto = new CompanyAddDTO(businessNumber, name, phone);

        // When & Then
        mockMvc.perform(
                post("/companies")
                        .content(new Gson().toJson(dto))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }
}
