package com.dh.clinica.dto.responses;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TurnResponseDTO {

    private Integer id;
    private PatientResponseDTO patient;
    private DentistResponseDTO dentist;
    private LocalDate date;
    private String hour;


}
