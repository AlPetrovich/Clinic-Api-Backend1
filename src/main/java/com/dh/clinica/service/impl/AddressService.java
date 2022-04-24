package com.dh.clinica.service.impl;

import com.dh.clinica.dto.AddressDTO;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.persistence.entities.Address;
import com.dh.clinica.persistence.repositories.IAddressRepository;
import com.dh.clinica.service.IAddressService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService implements IAddressService {

    private final Logger logger = Logger.getLogger(this.getClass());


    @Autowired
    private IAddressRepository addressRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public AddressDTO findById(Integer id) {
        logger.debug("findById address");
        Address address = addressRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Address","id",id));
        return mapDTO(address);
    }


    @Override
    public AddressDTO create(AddressDTO addressDTO) {
        logger.debug("create address");
        Address address = mapEntity(addressDTO);
        return mapDTO(addressRepository.save(address));
    }

    @Override
    public void deleteById(Integer id) {
        logger.debug("deleteById address");
        Address address = addressRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Address","id",id));
        addressRepository.delete(address);
    }


    @Override
    public AddressDTO update(AddressDTO addressDTO, Integer id) {
        logger.debug("update address");
        Address address = addressRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Address","id",id));
        address.setStreet(addressDTO.getStreet());
        address.setNumber(addressDTO.getNumber());
        address.setLocation(addressDTO.getLocation());
        address.setProvince(addressDTO.getProvince());
        return mapDTO(addressRepository.save(address));
    }

    @Override
    public List<AddressDTO> findAll() {
        logger.debug("findAll address");
        List<Address> addressList= addressRepository.findAll();
        List<AddressDTO> addressDTOList = addressList.stream().map(address -> mapDTO(address)).collect(Collectors.toList());
        return addressDTOList;
    }

    @Override
    public List<AddressDTO> findByLocation(String location) {
       List<Address> addressList =addressRepository.findByLocation(location)
               .orElseThrow(()-> new ResourceNotFoundException("Address","location",location));
       List<AddressDTO> addressDTOList = addressList.stream().map(address -> mapDTO(address)).collect(Collectors.toList());
       return addressDTOList;
    }

    //-------------------MODEL MAPPER ------------------------
    public AddressDTO mapDTO(Address address){
        AddressDTO addressDTO = modelMapper.map(address, AddressDTO.class);
        return addressDTO;
    }

    public Address mapEntity(AddressDTO addressDTO){
        Address address = modelMapper.map(addressDTO, Address.class);
        return address;
    }


}
