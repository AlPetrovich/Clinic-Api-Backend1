package com.dh.clinica.dto;
import lombok.*;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {

    private Integer id;
    private String name;
    private String lastname;
    private String dni;
    private LocalDate accessDate;
    private AddressDTO address;
    //private TurnoDTO turno;


    public PatientDTO(String name, String lastname, String dni, LocalDate accessDate, AddressDTO address) {
        this.name = name;
        this.lastname = lastname;
        this.dni = dni;
        this.accessDate = accessDate;
        this.address = address;
    }



    public PatientDTO(String name, String lastname, String dni, AddressDTO address) {
        this.name = name;
        this.lastname = lastname;
        this.dni = dni;
        this.address = address;
    }


}
