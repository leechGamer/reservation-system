package com.reservation.reservationsystem.controller;

import com.reservation.reservationsystem.entity.store.Store;
import com.reservation.reservationsystem.exception.NotFoundEntityException;
import com.reservation.reservationsystem.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/stores/{storeId}")
    public ResponseEntity getDetail(@PathVariable(name = "storeId") Long storeId) throws NotFoundEntityException {
        Store store = storeService.getDetail(storeId);
        return ResponseEntity.status(HttpStatus.OK).body(store);
    }
}
