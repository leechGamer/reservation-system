package com.reservation.reservationsystem.repository;

import com.reservation.reservationsystem.entity.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
