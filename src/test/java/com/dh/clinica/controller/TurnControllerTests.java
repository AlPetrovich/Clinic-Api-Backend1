package com.dh.clinica.controller;
import com.dh.clinica.controller.impl.TurnController;
import com.dh.clinica.dto.AddressDTO;
import com.dh.clinica.dto.DentistDTO;
import com.dh.clinica.dto.PatientDTO;
import com.dh.clinica.dto.TurnDTO;
import com.dh.clinica.service.IDentistService;
import com.dh.clinica.service.IPatientService;
import com.dh.clinica.service.ITurnService;
import com.dh.clinica.utils.Mapper;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.LocalDateTime;

@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TurnControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ITurnService turnService;
    private TurnDTO turnDTO;

    @Autowired
    private TurnController turnController;

    @Autowired
    private IPatientService patientService;
    private PatientDTO patientDTO;

    @Autowired
    private IDentistService dentistService;
    private DentistDTO dentistDTO;

    @BeforeEach
    public void setUp(){
        dentistDTO = new DentistDTO();
        dentistDTO.setId(2);
        dentistDTO.setName("Dentist");
        dentistDTO.setLastname("Dentist");
        dentistDTO.setLicence("111222");

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(2);
        addressDTO.setStreet("street");
        addressDTO.setNumber("1");
        addressDTO.setLocation("location");
        addressDTO.setProvince("province");

        patientDTO = new PatientDTO();
        patientDTO.setId(2);
        patientDTO.setName("Patient ale");
        patientDTO.setLastname("Patient ale");
        patientDTO.setDni("12345678");
        patientDTO.setAddress(addressDTO);

    }

  @Test
  public void createTurn() throws Exception {
      setUp();
      TurnDTO turnDTO = new TurnDTO();
      turnDTO.setDate(LocalDateTime.now());
      turnDTO.setDentist(dentistDTO);
      turnDTO.setPatient(patientDTO);
      mockMvc.perform(MockMvcRequestBuilders.post("/api/turn")
                      .contentType(MediaType.APPLICATION_JSON)
                      .characterEncoding("utf-8")
                      .content(Mapper.mapObjectToJson(turnDTO)))
              .andDo(MockMvcResultHandlers.print())
              .andExpect(MockMvcResultMatchers.status().isOk());
  }


    @Test
    public void getAllTurns() throws Exception {
        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/turn/list"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertFalse(response.getResponse().getContentAsString().isEmpty());
    }


}

