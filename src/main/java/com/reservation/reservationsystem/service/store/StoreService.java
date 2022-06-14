package com.reservation.reservationsystem.service.store;

import com.reservation.reservationsystem.entity.menu.Menu;
import com.reservation.reservationsystem.entity.store.Store;
import com.reservation.reservationsystem.exception.ErrorCode;
import com.reservation.reservationsystem.exception.NotFoundEntityException;
import com.reservation.reservationsystem.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

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

    public Set<Menu> getMenu(Long storeId) throws NotFoundEntityException {
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new NotFoundEntityException(ErrorCode.NOT_FOUND_ENTITY)
        );
        Set<Menu> menus = store.getMenus();
        return menus;
    }
}
