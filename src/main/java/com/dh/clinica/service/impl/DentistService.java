package com.dh.clinica.service.impl;
import com.dh.clinica.dto.DentistDTO;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.persistence.entities.Dentist;
import com.dh.clinica.persistence.repositories.IDentistRepository;
import com.dh.clinica.service.IDentistService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DentistService implements IDentistService {

    private final Logger logger = Logger.getLogger(this.getClass());


    @Autowired
    private IDentistRepository dentistRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public DentistDTO findById(Integer id) {
        logger.debug("findById DentistService");
        Dentist dentist = dentistRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Odontologo","id",id));
        return mapDTO(dentist);
    }


    @Override
    public DentistDTO create(DentistDTO dentistDTO) {
        logger.debug("create DentistService");
        if(dentistDTO == null){
            throw new ResourceNotFoundException("Odontologo","id", (Integer) null);
        }
        //DTO parameter pasamos a Entidad
        Dentist dentist = mapEntity(dentistDTO);
        //Guardamos entidad en base de datos
        Dentist newOdontologo = dentistRepository.save(dentist);
        //Retornamos DTO
        return mapDTO(newOdontologo);

    }

    @Override
    public void deleteById(Integer id) {
        logger.debug("deleteById DentistService");
        Dentist dentist = dentistRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Odontologo","id",id));
        dentistRepository.delete(dentist);
    }


    @Override
    public DentistDTO update(DentistDTO dentistDTO, Integer id) {
        logger.debug("update DentistService");
        //obtenemos entidad
        Dentist dentist = dentistRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Odontologo","id",id));
        //seteamos valores a la entidad
        dentist.setName(dentistDTO.getName());
        dentist.setLastname(dentistDTO.getLastname());
        dentist.setLicence(dentistDTO.getLicence());
        //guardo entidad
        Dentist dentistUpdate = dentistRepository.save(dentist);
        //retornamos objeto de transferencia
        return mapDTO(dentistUpdate);
    }

    @Override
    public List<DentistDTO> findAll() {
        logger.debug("findAll DentistService");
        List<Dentist> dentistList = dentistRepository.findAll();
        List<DentistDTO> dentistDTOS = dentistList.stream().map(dentist -> mapDTO(dentist)).collect(Collectors.toList());
        return dentistDTOS;
    }

    @Override
    public DentistDTO findByLicence(String licence) {
        Dentist dentist = dentistRepository.findByLicence(licence)
                .orElseThrow(()-> new ResourceNotFoundException("Odontologo","licencia",licence));
        return mapDTO(dentist);
    }

    @Override
    public List<DentistDTO> findByNameAndLastname(String name, String lastname) {
        List<Dentist> dentistList = dentistRepository.findByNameAndLastname(name,lastname)
                .orElseThrow(()-> new ResourceNotFoundException("Odontologo","nombre y apellido",name+" "+lastname));
        List<DentistDTO> dentistDTOS = dentistList.stream().map(dentist -> mapDTO(dentist)).collect(Collectors.toList());
        return dentistDTOS;
    }


    //-------------------MODEL MAPPER ------------------------
    private DentistDTO mapDTO(Dentist dentist){
        DentistDTO dentistDTO = modelMapper.map(dentist, DentistDTO.class);
       return dentistDTO;
    }

    public Dentist mapEntity(DentistDTO dentistDTO){
        Dentist dentist = modelMapper.map(dentistDTO, Dentist.class);
        return dentist;
    }


}
