package com.dh.clinica.service.impl;
import com.dh.clinica.dto.PatientDTO;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.persistence.entities.Patient;
import com.dh.clinica.persistence.repositories.IPatientRepository;
import com.dh.clinica.service.IPatientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Qualifier("patientService")
public class PatientService implements IPatientService {

    @Autowired
    private IPatientRepository patientRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PatientDTO findById(Integer id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", id));
        return mapDTO(patient);
    }


    @Override
    public PatientDTO create(PatientDTO patientDTO) {
        if(patientDTO == null){
            throw new ResourceNotFoundException("Patient", "id", null);
        }
        //seteo fecha ingreso
        patientDTO.setAccessDate(LocalDate.now());
        //guardo paciente como entidad
        Patient patient = mapEntity(patientDTO);
        Patient savePatientResponse = patientRepository.save(patient);

        return mapDTO(savePatientResponse);
    }

    @Override
    public void deleteById(Integer id) {
         Patient patient = patientRepository.findById(id)
                 .orElseThrow((() -> new ResourceNotFoundException("Patient", "id", id)));
         patientRepository.delete(patient);
    }

    // -------------------VER-------------
    @Override
    public PatientDTO update(PatientDTO patientDTO, Integer id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow((() -> new ResourceNotFoundException("Patient", "id", id)));

        patient.setName(patientDTO.getName());
        patient.setLastname(patientDTO.getLastname());
        patient.setDni(patientDTO.getDni());
        patient.setAccessDate(patientDTO.getAccessDate());
        //2-opciones
        //paciente.setDomicilio(pacienteDTO.getDomicilioDTO().toEntity());
        patient.setAddress(addressService.mapEntity(patientDTO.getAddress()));
        Patient updatedPatient = patientRepository.save(patient);

        return mapDTO(updatedPatient);
    }

    @Override
    public List<PatientDTO> findAll() {
        List<Patient> patientList = patientRepository.findAll();
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
