package com.reservation.reservationsystem.service.store;

import com.reservation.reservationsystem.dto.store.StoreSaveDTO;
import com.reservation.reservationsystem.entity.company.Company;
import com.reservation.reservationsystem.entity.menu.Menu;
import com.reservation.reservationsystem.entity.store.Store;
import com.reservation.reservationsystem.exception.ErrorCode;
import com.reservation.reservationsystem.exception.NotFoundEntityException;
import com.reservation.reservationsystem.repository.CompanyRepository;
import com.reservation.reservationsystem.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    private final CompanyRepository companyRepository;

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

    @Transactional
    public void save(Long companyId, StoreSaveDTO storeSaveDTO) {
        Store store = storeSaveDTO.toEntity();
        Optional<Company> company = companyRepository.findById(companyId);
        company.ifPresentOrElse(
                c -> {
                    c.addStore(store);
                    store.setCompany(c);
                    storeRepository.save(store);
                },
                () -> new NotFoundEntityException(ErrorCode.NOT_FOUND_ENTITY)
        );
    }
}
