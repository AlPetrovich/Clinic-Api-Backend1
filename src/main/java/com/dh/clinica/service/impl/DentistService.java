package com.dh.clinica.service.impl;
import com.dh.clinica.dto.DentistDTO;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.persistence.entities.Dentist;
import com.dh.clinica.persistence.repositories.IDentistRepository;
import com.dh.clinica.service.IDentistService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DentistService implements IDentistService {


    @Autowired
    private IDentistRepository dentistRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public DentistDTO findById(Integer id) {
        Dentist dentist = dentistRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Odontologo","id",id));
        return mapDTO(dentist);
    }

    @Override
    public DentistDTO create(DentistDTO dentistDTO) {
        if(dentistDTO == null){
            throw new ResourceNotFoundException("Odontologo","id",null);
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
        Dentist dentist = dentistRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Odontologo","id",id));
        dentistRepository.delete(dentist);
    }

    @Override
    public DentistDTO update(DentistDTO dentistDTO, Integer id) {
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
        List<Dentist> dentistList = dentistRepository.findAll();
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
