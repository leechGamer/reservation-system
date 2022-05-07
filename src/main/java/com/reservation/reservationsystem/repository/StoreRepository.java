package com.reservation.reservationsystem.repository;

import com.reservation.reservationsystem.entity.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {

}
