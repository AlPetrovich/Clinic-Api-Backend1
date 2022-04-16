package com.dh.clinica.dto.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientResponseDTO {

    private String name;
    private String lastname;

    public PatientResponseDTO(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }
}
