package com.reservation.reservationsystem.dto.menu;

import com.reservation.reservationsystem.entity.contstants.CategoryType;
import com.reservation.reservationsystem.entity.menu.Menu;
import lombok.*;
import lombok.experimental.Tolerate;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class MenuSaveRequestDTO {

    @NotEmpty(message = "메뉴 이름을 입력하세요.")
    private String name;

    @NotNull(message = "가격을 입력해 주세요")
    @DecimalMin(value = "10.00", message = "가격은 100원 이상으로 입력해주세요")
    private Long amount;

    private CategoryType category;

    private String description;

    @Tolerate
    MenuSaveRequestDTO() {}

    @Builder
    public MenuSaveRequestDTO (
            String name,
            Long amount,
            CategoryType category,
            String description
    ) {
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.description= description;
    }

    public Menu toEntity() {
        return Menu.of(name, category, description, amount);
    }
}
