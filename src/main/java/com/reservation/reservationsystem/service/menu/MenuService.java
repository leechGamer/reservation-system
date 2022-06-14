package com.reservation.reservationsystem.service.menu;

import com.reservation.reservationsystem.dto.menu.MenuSaveRequestDTO;
import com.reservation.reservationsystem.entity.menu.Menu;
import com.reservation.reservationsystem.entity.store.Store;
import com.reservation.reservationsystem.repository.menu.MenuRepository;
import com.reservation.reservationsystem.repository.store.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void save(Long storeId, MenuSaveRequestDTO requestDTO) {
        Optional<Store> store = storeRepository.findById(storeId);
        Menu menu = requestDTO.toEntity();
        store.ifPresent(s -> {
            store.get().addMenu(menu);
            menuRepository.save(menu);
        });
    }
}
