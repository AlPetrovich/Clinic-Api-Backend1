package com.dh.clinica.controllers;
import com.dh.clinica.controller.impl.PatientController;
import com.dh.clinica.dto.AddressDTO;
import com.dh.clinica.dto.PatientDTO;
import com.dh.clinica.exceptions.ClinicAppException;
import com.dh.clinica.exceptions.GlobalExceptionHandler;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.service.IPatientService;
import com.dh.clinica.utils.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.doThrow;


@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(PatientControllerTest.class) //probar la capa de controlador
@AutoConfigureMockMvc(addFilters = false)
public class PatientControllerTest {

    private MockMvc mockMvc; //clase que permite hacer llamadas web a un controllador (peticiones a API)

    @Mock //realizamos simulacion de un servicio
    private IPatientService patientService; //mock del servicio de pacientes

    @InjectMocks
    private PatientController patientController; //mock del controlador de pacientes

    private PatientDTO patientDTO;
    private PatientDTO patientNonExistent;
    private PatientDTO patientExistent;

    @Before
    public void reset() throws ClinicAppException, ResourceNotFoundException {
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).setControllerAdvice(GlobalExceptionHandler.class).build();
        AddressDTO addressDTO = new AddressDTO("Calle falsa 123", "Ciudad falsa", "12345", "España");
        patientDTO = new PatientDTO("Alexis","Petrovich","40888999",addressDTO);
        AddressDTO addressWithId = new AddressDTO("Calle falsa", "Ciudad", "12345", "España");
        addressWithId.setId(1);
        patientNonExistent = new PatientDTO("Alexis","Petrovich","40888999",addressWithId);
        patientExistent = new PatientDTO("Alexis","Petrovich","40888999",addressWithId);
        patientExistent.setId(1);
        patientExistent.setAccessDate(LocalDate.now());
        patientNonExistent.setId(222);
        patientNonExistent.setAccessDate(LocalDate.now());
        configureMockito();
    }

    private void configureMockito() throws ClinicAppException, ResourceNotFoundException {
        Mockito.when(patientService.findById(222)).thenThrow(new ResourceNotFoundException("Patient not found"));
        Mockito.when(patientService.findById(1)).thenReturn(patientExistent);
        Mockito.when(patientService.update(patientExistent, patientExistent.getId())).thenReturn(patientExistent);
        Mockito.when(patientService.update(patientNonExistent, patientNonExistent.getId())).thenThrow(new ResourceNotFoundException("Patient not found"));
        doThrow(new ResourceNotFoundException("Patient not found with id : 222")).when(patientService).deleteById(222);
    }

    @Test
    public void testRegisterPatient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/patient")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .content(Mapper.mapObjectToJson(patientDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void test03buscarPacientePorIdExistenteDevuelveOk() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/api/patient/{id}", "1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertEquals(Mapper.mapObjectToJson(patientExistent), response.getResponse().getContentAsString());
    }

}
