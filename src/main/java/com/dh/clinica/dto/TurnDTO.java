package com.dh.clinica.dto;
import lombok.*;

import java.time.LocalDateTime;


@Data
public class TurnDTO {

    private Integer id;
    private LocalDateTime date;
    //---relaciones
    private PatientDTO patient;
    private DentistDTO dentist;

    public TurnDTO( LocalDateTime date, PatientDTO patient, DentistDTO dentist) {
        this.date = date;
        this.patient = patient;
        this.dentist = dentist;
    }

    public TurnDTO() {
    }


}
