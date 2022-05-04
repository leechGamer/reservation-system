package com.reservation.reservationsystem.controller;

import com.reservation.reservationsystem.dto.menu.MenuSaveRequestDTO;
import com.reservation.reservationsystem.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/businesses/stores/{storeId}/menus")
    public ResponseEntity.BodyBuilder create(@PathVariable("storeId") Long storeId, @RequestBody @Valid MenuSaveRequestDTO requestDTO) {
        this.menuService.save(storeId, requestDTO);
        return ResponseEntity.ok();
    }
}
