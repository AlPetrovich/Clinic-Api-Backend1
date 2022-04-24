package com.dh.clinica.controller;


import com.dh.clinica.controller.impl.PatientController;
import com.dh.clinica.dto.AddressDTO;
import com.dh.clinica.dto.PatientDTO;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.utils.Mapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.LocalDate;


@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PatientController patientController;


    public void setUp() throws ResourceNotFoundException {
        AddressDTO address = new AddressDTO("calle oro","333","caba","Buenos Aires");
        patientController.create(new PatientDTO("Ema","Moreno","33666777", LocalDate.of(1990,10,10),address));
        AddressDTO addressNew = new AddressDTO("calle hipolito","555","sf","Buenos Aires");
        patientController.create(new PatientDTO("Josefina","Garcia","45678987", LocalDate.of(1990,10,10),addressNew));
    }

    @Test
    public void create() throws Exception {
        AddressDTO address = new AddressDTO("calle oro","333","caba","Buenos Aires");
        PatientDTO patient = new PatientDTO("Ema","Moreno","33666777", LocalDate.of(1990,10,10),address);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(Mapper.mapObjectToJson(patient)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void getAllPatients() throws Exception {
        this.setUp();
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/api/patient/list")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertFalse(response.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void getPatient() throws Exception {
        this.setUp();
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/api/patient/3")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertFalse(response.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void getPatientNotFound() throws Exception {
        this.setUp();
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/api/patient/100")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
        Assert.assertFalse(response.getResponse().getContentAsString().isEmpty());
    }


    @Test
    public void deletePatient() throws Exception {
        this.setUp();
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/patient/127")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertFalse(response.getResponse().getContentAsString().isEmpty());
    }
}
