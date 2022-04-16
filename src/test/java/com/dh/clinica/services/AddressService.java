package com.dh.clinica.services;
import com.dh.clinica.dto.AddressDTO;
import com.dh.clinica.service.IAddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.*;


@SpringBootTest
public class AddressService {

    @Autowired
    private IAddressService iAddressService;
    private AddressDTO addressDTO;

    @BeforeEach
    public void setUp() {
        addressDTO = new AddressDTO();
        addressDTO.setStreet("street 12");
        addressDTO.setNumber("88");
        addressDTO.setLocation("sp");
        addressDTO.setProvince("Chaco");
    }

    @Test
    public void testSaveAddress() {
        AddressDTO result = iAddressService.create(addressDTO);
        assertNotNull(iAddressService.findById(result.getId()));
    }

    @Test
    public void testUpdateAddress(){
        AddressDTO result = iAddressService.create(addressDTO);
        AddressDTO addressDB = iAddressService.findById(result.getId());
        addressDB.setStreet("street 12333333");
        AddressDTO addressUpdate = iAddressService.update(addressDB, addressDB.getId());
        assertNotEquals(addressUpdate, result);
    }

    @Test
    public void testDeleteAddress(){
        AddressDTO result = iAddressService.create(addressDTO);
        assertNotNull(iAddressService.findById(result.getId()));
        iAddressService.deleteById(result.getId());
        assertThrows(Exception.class, () -> iAddressService.findById(result.getId()));
    }

    @Test
    public void testFindById(){
        AddressDTO result = iAddressService.create(addressDTO);
        assertNotNull(iAddressService.findById(result.getId()));
    }

    @Test
    public void testFindAll(){
        AddressDTO result = iAddressService.create(addressDTO);
        assertNotNull(iAddressService.findAll());
    }
}
