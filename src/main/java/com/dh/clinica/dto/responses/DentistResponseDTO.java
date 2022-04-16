package com.dh.clinica.dto.responses;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DentistResponseDTO {

    private String name;
    private String lastName;

    public DentistResponseDTO(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }
}
