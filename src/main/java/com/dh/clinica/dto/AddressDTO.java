package com.dh.clinica.dto;
import com.dh.clinica.persistence.entities.Address;
import lombok.*;

@Data
public class AddressDTO {

    private Integer id;
    private String street;
    private String number;
    private String location;
    private String province;

    public AddressDTO(String street, String number, String location, String province) {
        this.street = street;
        this.number = number;
        this.location = location;
        this.province = province;
    }

    public AddressDTO() {
    }

    public Address toEntity(){
        Address entity = new Address();
        entity.setId(id);
        entity.setStreet(street);
        entity.setLocation(location);
        entity.setProvince(province);
        entity.setNumber(number);

        return entity;
    }
}
