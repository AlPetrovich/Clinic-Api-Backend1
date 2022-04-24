package com.dh.clinica.dto;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class DentistDTO {


    private Integer id;

    @NotEmpty
    @Size(min = 2, message = "El nombre del Odontologo deberia tener al menos 2 caracteres")
    private String name;

    @Size(min = 2, message = "El apellido del Odontologo deberia tener al menos 2 caracteres")
    private String lastname;

    @Size(min = 5,max = 10, message = "La Matricula del Odontologo deberia tener entre 5 y 10 caracteres")
    private String licence;

    public DentistDTO( String name, String lastname, String licence) {
        this.name = name;
        this.lastname = lastname;
        this.licence = licence;
    }

    public DentistDTO() {

    }


}
