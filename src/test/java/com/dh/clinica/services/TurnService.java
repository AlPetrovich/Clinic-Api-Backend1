package com.dh.clinica.services;
import com.dh.clinica.dto.AddressDTO;
import com.dh.clinica.dto.DentistDTO;
import com.dh.clinica.dto.PatientDTO;
import com.dh.clinica.dto.TurnDTO;
import com.dh.clinica.service.IDentistService;
import com.dh.clinica.service.IPatientService;
import com.dh.clinica.service.ITurnService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.Assert.*;


@SpringBootTest
public class TurnService {

    @Autowired
    private ITurnService turnService;

    @Autowired
    private IPatientService patientService;
    private PatientDTO patientDTO;

    @Autowired
    private IDentistService dentistService;
    private DentistDTO dentistDTO;

    @BeforeEach
    public void setUp(){
        dentistDTO = new DentistDTO();
        dentistDTO.setName("Dentist");
        dentistDTO.setLastname("Dentist");
        dentistDTO.setLicence("111222");

        AddressDTO addressDTO = new AddressDTO("calle","111","sp","chaco");
        patientDTO = new PatientDTO("Alexis","Petrovich","44555999",addressDTO);
    }

    @Test
    public void createTurnWithPatientAndDentistExistsTest() throws Exception {
        DentistDTO dentistDTO = dentistService.create(this.dentistDTO);
        PatientDTO patientDTO = patientService.create(this.patientDTO);
        TurnDTO turnDTO = new TurnDTO();
        turnDTO.setDentist(dentistDTO);
        turnDTO.setPatient(patientDTO);
        turnDTO.setDate(LocalDateTime.now());
        TurnDTO turnDTO1 = turnService.create(turnDTO);
        assertNotNull(turnService.findById(turnDTO1.getId()));
    }

   @Test
    public void createTurnWithoutPatientAndDentist(){
        TurnDTO turnDTO = new TurnDTO();
        turnDTO.setDate(LocalDateTime.now());
        assertThrows(Exception.class, () -> turnService.create(turnDTO));
    }

    @Test
    public void createTurnWithPatientAndDentistNotExists(){
        TurnDTO turnDTO = new TurnDTO();
        turnDTO.setDentist(dentistDTO);
        turnDTO.setPatient(patientDTO);
        turnDTO.setDate(LocalDateTime.now());
        assertThrows(Exception.class, () -> turnService.create(turnDTO));
    }

    @Test
    public void createTurnWithPatientAndDentistNull(){
        TurnDTO turnDTO = new TurnDTO();
        turnDTO.setDentist(null);
        turnDTO.setPatient(null);
        turnDTO.setDate(LocalDateTime.now());
        assertThrows(Exception.class, () -> turnService.create(turnDTO));
    }

    @Test
    public void updateTurnWithPatientAndDentistExistsTest() throws Exception {
        DentistDTO dentistDTO = dentistService.create(this.dentistDTO);
        PatientDTO patientDTO = patientService.create(this.patientDTO);
        TurnDTO turnDTO = new TurnDTO();
        turnDTO.setDentist(dentistDTO);
        turnDTO.setPatient(patientDTO);
        turnDTO.setDate(LocalDateTime.now());
        TurnDTO turnDTO1 = turnService.create(turnDTO);
        turnDTO1.setDate(LocalDateTime.now().plusDays(1));
        turnService.update(turnDTO1, turnDTO1.getId());
        assertNotEquals(turnDTO.getDate(),turnService.findById(turnDTO1.getId()).getDate());
    }

    @Test
    public void deleteTurnWithPatientAndDentistExistsTest() throws Exception {
        DentistDTO dentistDTO = dentistService.create(this.dentistDTO);
        PatientDTO patientDTO = patientService.create(this.patientDTO);
        TurnDTO turnDTO = new TurnDTO();
        turnDTO.setDentist(dentistDTO);
        turnDTO.setPatient(patientDTO);
        turnDTO.setDate(LocalDateTime.now());
        TurnDTO turnDTO1 = turnService.create(turnDTO);
        turnService.deleteById(turnDTO1.getId());
        assertThrows(Exception.class, () -> turnService.findById(turnDTO1.getId()));
    }

}
