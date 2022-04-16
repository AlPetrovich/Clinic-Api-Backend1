package com.dh.clinica.services;
import com.dh.clinica.dto.AddressDTO;
import com.dh.clinica.dto.PatientDTO;
import com.dh.clinica.service.impl.PatientService;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class PatientServiceTest {

    @Autowired
    private PatientService patientService;
    private PatientDTO patient;

    @BeforeEach
    public void settings(){
        AddressDTO addressDTO = new AddressDTO("calle","111","sp","chaco");
        patient = new PatientDTO("Alexis","Petrovich","44555999",addressDTO);
    }

    @Test
    public void savePatient() throws Exception{
        PatientDTO patientDTO = patientService.create(patient);
        assertNotNull(patientService.findById(patientDTO.getId()));
    }

    @Test
    public void updatePatient() throws Exception{
        PatientDTO patientDTO = patientService.create(patient);
        patientDTO.setName("Alexis");
        patientDTO = patientService.update(patientDTO,patientDTO.getId());
        assertEquals("Alexis",patientDTO.getName());
    }

    @Test
    public void deletePatient() throws Exception{
        PatientDTO patientDTO = patientService.create(patient);
        assertNotNull(patientService.findById(patientDTO.getId()));
        patientService.deleteById(patientDTO.getId());
        assertThrows(Exception.class, ()-> patientService.findById(patientDTO.getId()));
    }

    @Test
    public void findAllPatients() throws Exception{
        PatientDTO patientDTO = patientService.create(patient);
        assertNotNull(patientService.findAll());
    }

}
