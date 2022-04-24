package com.dh.clinica.service;
import com.dh.clinica.dto.DentistDTO;

import java.util.List;

public interface IDentistService extends ICRUDService<DentistDTO>{

    public DentistDTO findByLicence(String licence);

    public List<DentistDTO> findByNameAndLastname(String name, String lastname);


}
