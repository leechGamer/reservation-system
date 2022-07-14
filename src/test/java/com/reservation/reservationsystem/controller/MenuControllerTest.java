package com.reservation.reservationsystem.controller;

import com.google.gson.Gson;
import com.reservation.reservationsystem.dto.menu.MenuSaveRequestDTO;
import com.reservation.reservationsystem.entity.menu.Menu;
import com.reservation.reservationsystem.service.menu.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class MenuControllerTest {

    @Mock
    private MenuController menuController;

    @InjectMocks
    private MenuService menuService;

    private MockMvc mockMvc;

    private Gson gson;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(menuController).build();
        gson = new Gson();
    }

    @Test
    @DisplayName("menu를 등록 - success")
    public void test() throws Exception {

        // Given
        MenuSaveRequestDTO menu = new MenuSaveRequestDTO (
                "menuName",
                1000L,
                null,
                null
        );

        // When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.post("/businesses/stores/{storeId}/menus", 1)
                        .content(gson.toJson(menu))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("menu를 등록 - invalid parameter")
    @Test
    public void testWithInvalidParam() throws Exception {
        // Given
        Menu menu = new MenuSaveRequestDTO(
                null,
                5L,
                null,
                null
        ).toEntity();

        // When & Then
        mockMvc.perform(
                MockMvcRequestBuilders.post("/businesses/stores/{storeId}/menus", 2)
                        .content(new Gson().toJson(menu))
                        .content(gson.toJson(menu))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }
}
