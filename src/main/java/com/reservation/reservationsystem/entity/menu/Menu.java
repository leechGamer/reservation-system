package com.reservation.reservationsystem.entity.menu;

import com.reservation.reservationsystem.entity.Audit;
import com.reservation.reservationsystem.entity.contstants.CategoryType;
import com.reservation.reservationsystem.entity.store.Store;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Table(name = "menu")
@Builder
public class Menu extends Audit {

    @Id
    @GeneratedValue
    @Column(name = "menu_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private CategoryType category;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private Long amount;

    @ManyToOne
    private Store store;

    @Tolerate
    public Menu() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(name, menu.name) && Objects.equals(store.getId(), menu.store.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, store.getId());
    }

    public static Menu of(
            String name,
            CategoryType category,
            String description,
            Long amount
    ) {
        return builder()
                .name(name)
                .category(category)
                .description(description)
                .amount(amount)
                .build();
    }
    
    public void setStore(Store store) {
        if (store == null) {
            throw new EntityExistsException();
        }
        this.store = store;
    }
}
