package com.dh.clinica.services;
import com.dh.clinica.controller.impl.PatientController;
import com.dh.clinica.dto.AddressDTO;
import com.dh.clinica.dto.PatientDTO;
import com.dh.clinica.exceptions.GlobalExceptionHandler;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.service.impl.PatientService;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.Mockito.doThrow;


@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(PatientController.class)
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class PatientServiceMock {

    private MockMvc mockMvc;

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    private PatientDTO patient;
    private PatientDTO patientExist;
    private PatientDTO patientNotExist;
    private AddressDTO addressDTO;

    @Before
    public void setUp() throws ResourceNotFoundException {
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).setControllerAdvice(GlobalExceptionHandler.class).build();
        addressDTO = new AddressDTO("Calle", "123", "SP", "Chaco");
        patient = new PatientDTO("Peter", "Garcia", "43434443", LocalDate.now(), addressDTO);
        patientNotExist = new PatientDTO("Peter", "Garcia", "pp", LocalDate.now(), addressDTO);
        patientExist = new PatientDTO("Peter", "Garcia", "33333322", LocalDate.now(), addressDTO);
        patientExist.setId(1);
        patientNotExist.setId(999);
        configureMockito();
    }

    public void configureMockito() throws ResourceNotFoundException{
        Mockito.when(patientService.create(patient)).thenReturn(patientExist);
        Mockito.when(patientService.findById(1)).thenReturn(patientExist);
        Mockito.when(patientService.findById(999)).thenThrow(new ResourceNotFoundException("Patient not found"));
        Mockito.when(patientService.update(patientNotExist, patientNotExist.getId())).thenThrow(new ResourceNotFoundException("Patient not found"));
        Mockito.when(patientService.update(patientExist, patientExist.getId())).thenReturn(patientExist);
        doThrow(new ResourceNotFoundException("Patient not found")).when(patientService).deleteById(10);
    }

    @org.junit.Test
    public void testCreatePatient() throws Exception {
        patientService.create(patient);
        Mockito.verify(patientService).create(patient);
    }

    @org.junit.Test
    public void testDeletePatient() throws Exception {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> patientService.deleteById(10));
    }

    @org.junit.Test
    public void testFindByIdExist() throws Exception {
        patientService.findById(1);
        Mockito.verify(patientService).findById(1);
    }

    @org.junit.Test
    public void testFindByIdNotExist() throws Exception {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> patientService.findById(999));
    }

    @org.junit.Test
    public void testUpdatePatientExist() throws Exception {
        patientService.update(patientExist, patientExist.getId());
        Mockito.verify(patientService).update(patientExist, patientExist.getId());
    }

    @org.junit.Test
    public void testUpdatePatientNotExist() throws Exception {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> patientService.update(patientNotExist, patientNotExist.getId()));
    }
}

