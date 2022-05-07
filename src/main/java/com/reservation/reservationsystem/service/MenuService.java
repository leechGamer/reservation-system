package com.reservation.reservationsystem.service;

import com.reservation.reservationsystem.dto.menu.MenuSaveRequestDTO;
import com.reservation.reservationsystem.entity.menu.Menu;
import com.reservation.reservationsystem.entity.store.Store;
import com.reservation.reservationsystem.repository.MenuRepository;
import com.reservation.reservationsystem.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MenuService {

    private MenuRepository menuRepository;
    private StoreRepository storeRepository;

    public MenuService(
            MenuRepository menuRepository,
            StoreRepository storeRepository
    ) {
        this.menuRepository = menuRepository;
        this.storeRepository = storeRepository;
    }

    public void save(Long storeId, MenuSaveRequestDTO requestDTO) {
        Optional<Store> store = storeRepository.findById(storeId);
        Menu menu = requestDTO.toEntity();
        store.ifPresent(s -> {
            store.get().addMenu(menu);
            menuRepository.save(menu);
        });
    }
}
