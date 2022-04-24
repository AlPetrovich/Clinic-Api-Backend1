package com.dh.clinica.service.impl;
import com.dh.clinica.dto.PatientDTO;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.persistence.entities.Patient;
import com.dh.clinica.persistence.repositories.IPatientRepository;
import com.dh.clinica.service.IPatientService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

import java.util.stream.Collectors;

@Service
public class PatientService implements IPatientService {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private IPatientRepository patientRepository;

    @Autowired
    private AddressService addressService;


    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PatientDTO findById(Integer id) {
        logger.debug("PatientService.findById()");
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", id));
        return mapDTO(patient);
    }



    @Override
    public PatientDTO create(PatientDTO patientDTO) {
        logger.debug("PatientService.create()");
        if(patientDTO == null){
            throw new ResourceNotFoundException("Patient", "id", (Integer) null);
        }
        patientDTO.setAccessDate(LocalDate.now());
        Patient patient =  patientRepository.save(mapEntity(patientDTO));
        return mapDTO(patient);
    }

    @Override
    public void deleteById(Integer id) {
        logger.debug("PatientService.deleteById()");
         Patient patient = patientRepository.findById(id)
                 .orElseThrow((() -> new ResourceNotFoundException("Patient", "id", id)));
         patientRepository.delete(patient);
    }


    @Override
    public PatientDTO update(PatientDTO patientDTO, Integer id) {
        logger.debug("PatientService.update()");
        Patient patient = patientRepository.findById(id)
                .orElseThrow((() -> new ResourceNotFoundException("Patient", "id", id)));
        patient.setName(patientDTO.getName());
        patient.setLastname(patientDTO.getLastname());
        patient.setDni(patientDTO.getDni());
        patient.setAccessDate(patientDTO.getAccessDate());
        patient.setAddress(addressService.mapEntity(patientDTO.getAddress()));
        Patient updatedPatient = patientRepository.save(patient);
        return mapDTO(updatedPatient);
    }

    @Override
    public List<PatientDTO> findAll() {
        logger.debug("PatientService.findAll()");
        List<Patient> patientList = patientRepository.findAll();
        List<PatientDTO> patientDTOS = patientList.stream().map(patient -> mapDTO(patient)).collect(Collectors.toList());
        return patientDTOS;
    }


    @Override
    public PatientDTO findByDni(String dni) {
        Patient patient = patientRepository.findByDni(dni)
                .orElseThrow((() -> new ResourceNotFoundException("Patient", "dni", dni)));
        return mapDTO(patient);
    }

    @Override
    public List<PatientDTO> findByName(String name) {
        List<Patient> patientList = patientRepository.findByName(name)
                .orElseThrow((() -> new ResourceNotFoundException("Patient", "name", name)));
        List<PatientDTO> patientDTOS = patientList.stream().map(patient -> mapDTO(patient)).collect(Collectors.toList());
        return patientDTOS;
    }

    //-------------------MODEL MAPPER ------------------------
    private PatientDTO mapDTO(Patient patient){
        PatientDTO patientDTO = modelMapper.map(patient, PatientDTO.class);
        return patientDTO;
    }

    public Patient mapEntity(PatientDTO patientDTO){
        Patient patient = modelMapper.map(patientDTO, Patient.class);
        return patient;
    }


}
