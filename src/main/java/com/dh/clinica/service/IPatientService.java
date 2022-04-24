package com.dh.clinica.service;
import com.dh.clinica.dto.PatientDTO;

import java.util.List;


public interface IPatientService extends ICRUDService<PatientDTO>{

    PatientDTO findByDni(String dni);

    List<PatientDTO> findByName(String name);
}
