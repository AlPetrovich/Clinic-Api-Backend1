package com.dh.clinica.service;
import com.dh.clinica.dto.AddressDTO;

import java.util.List;


public interface IAddressService extends ICRUDService<AddressDTO>{

    public List<AddressDTO> findByLocation(String location);
}
