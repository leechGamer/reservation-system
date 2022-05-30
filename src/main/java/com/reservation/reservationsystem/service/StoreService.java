package com.reservation.reservationsystem.service;

import com.reservation.reservationsystem.entity.store.Store;
import com.reservation.reservationsystem.exception.ErrorCode;
import com.reservation.reservationsystem.exception.NotFoundEntityException;
import com.reservation.reservationsystem.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public Store getDetail(Long storeId) throws NotFoundEntityException {
        Store store = storeRepository.findById(
                storeId).orElseThrow(() -> new NotFoundEntityException(ErrorCode.NOT_FOUND_ENTITY)
        );
        return store;
    }
}
