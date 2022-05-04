package com.reservation.reservationsystem.entity.menu;

import com.reservation.reservationsystem.entity.Audit;
import com.reservation.reservationsystem.entity.contstants.CategoryType;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Entity
@Getter
@Builder
@Table(name = "menu")
public class Menu extends Audit {

    @Id
    @GeneratedValue
    @Column(name = "menu_id")
    private Long id;

    // Todo:: storeId 추가

    @Column(nullable = false)
    private String name;

    private CategoryType category;

    private String description;

    @Column(nullable = false)
    private Long amount;

    @Tolerate
    public Menu() {}

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
}
