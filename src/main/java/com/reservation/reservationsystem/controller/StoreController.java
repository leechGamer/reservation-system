package com.reservation.reservationsystem.controller;

import com.reservation.reservationsystem.entity.menu.Menu;
import com.reservation.reservationsystem.entity.store.Store;
import com.reservation.reservationsystem.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/stores/{storeId}")
    public ResponseEntity getDetail(@PathVariable(name = "storeId") Long storeId) {
        Store store = storeService.getDetail(storeId);
        return ResponseEntity.status(HttpStatus.OK).body(store);
    }

    @GetMapping("/stores/{storeId}/menu")
    public ResponseEntity getMenu(@PathVariable(name = "storeId") Long storeId) {
        Set<Menu> menus = this.storeService.getMenu(storeId);
        return ResponseEntity.status(HttpStatus.OK).body(menus);
    }
}
