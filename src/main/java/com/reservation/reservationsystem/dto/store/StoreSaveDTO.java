package com.reservation.reservationsystem.dto.store;

import com.reservation.reservationsystem.entity.contstants.StoreCategory;
import com.reservation.reservationsystem.entity.store.Address;
import com.reservation.reservationsystem.entity.store.Location;
import com.reservation.reservationsystem.entity.store.Store;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class StoreSaveDTO {

    @NotEmpty(message = "이름을 추가해 주세요")
    private String name;

    private StoreCategory category;

    private String description;

    @NotEmpty(message = "주소를 추가해 주세요")
    private String address;

    @NotEmpty(message = "우편번호를 추가해 주세요")
    private String zipcode;

    @NotNull(message = "위치정보를 등록해 주세요")
    private double latitude;

    @NotNull(message = "위치정보를 등록해 주세요")
    private double longitude;

    @NotEmpty(message = "전화번호를 입력해 주세요")
    private String phoneNumber;

    @Builder
    public StoreSaveDTO(String name, StoreCategory category, String description, String address, String zipcode, double latitude, double longitude, String phoneNumber) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.address = address;
        this.zipcode = zipcode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNumber = phoneNumber;
    }

    public Store toEntity() {
        Address address2 = Address.of(address, zipcode);
        Location location = Location.of(latitude, longitude);

        return Store.of(
                name,
                category,
                description,
                location,
                address2,
                phoneNumber
        );
    }
}
