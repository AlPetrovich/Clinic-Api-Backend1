package com.dh.clinica.services;
import com.dh.clinica.dto.DentistDTO;
import com.dh.clinica.service.IDentistService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

@SpringBootTest
public class DentistService {

    @Autowired
    private IDentistService dentistService;
    private DentistDTO dentistDTO;

    @BeforeEach
    public void setUp() {
        dentistDTO = new DentistDTO();
        dentistDTO.setName("Dentist");
        dentistDTO.setLastname("Dentist");
        dentistDTO.setLicence("111222");
    }

   @Test
    public void testSaveDentist() {
        DentistDTO result = dentistService.create(dentistDTO);
        assertEquals(result.getName(), dentistDTO.getName());
    }

    @Test
    public void testUpdateDentist(){
        DentistDTO result = dentistService.create(dentistDTO);
        DentistDTO getCreate = dentistService.findById(result.getId());
        getCreate.setName("DentistUpdate");
        DentistDTO update = dentistService.update(result, result.getId());
        //No deben ser iguales
        assertNotEquals(update, getCreate);

    }

    @Test
    public void testDeleteDentist() throws Exception {
        DentistDTO result = dentistService.create(dentistDTO);
        assertNotNull(dentistService.findById(result.getId()));
        dentistService.deleteById(result.getId());
        assertThrows(Exception.class, () -> dentistService.findById(result.getId()));
    }

    @Test
    public void testFindByIdDentist() throws Exception {
        DentistDTO result = dentistService.create(dentistDTO);
        assertNotNull(dentistService.findById(result.getId()));
    }

    @Test
    public void testFindAllDentist() throws Exception {
        DentistDTO result = dentistService.create(dentistDTO);
        assertNotEquals(0, dentistService.findAll().size());
    }
}

