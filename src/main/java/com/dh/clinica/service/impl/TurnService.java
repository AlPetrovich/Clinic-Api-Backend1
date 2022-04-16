package com.dh.clinica.service.impl;
import com.dh.clinica.dto.DentistDTO;
import com.dh.clinica.dto.PatientDTO;
import com.dh.clinica.dto.TurnDTO;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.persistence.entities.Turn;
import com.dh.clinica.persistence.repositories.ITurnRepository;
import com.dh.clinica.service.ITurnService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Qualifier("turnService")
public class TurnService implements ITurnService {

    @Autowired
    private ITurnRepository turnRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DentistService dentistService;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public TurnDTO findById(Integer id) {
       Turn turn = turnRepository.findById(id)
               .orElseThrow(()-> new ResourceNotFoundException("Turn not found", "id", id));
       return mapDTO(turn);
    }

    @Override
    public TurnDTO create(TurnDTO turnDTO) {
        //Nos aseguramos que tanto el odontologo como el paciente existan en la base de datos
        PatientDTO patientDTO = patientService.findById(turnDTO.getPatient().getId());
        DentistDTO dentistDTO= dentistService.findById(turnDTO.getDentist().getId());

        //Creamos el turno
        Turn turn = mapEntity(turnDTO);
        //ver fecha
        turn.setDate(turnDTO.getDate());
        turn.setPatient(patientService.mapEntity(patientDTO));
        turn.setDentist(dentistService.mapEntity(dentistDTO));
        //Guardamos el turno en la base de datos
        turn = turnRepository.save(turn);
        //Retornamos el turno
        return mapDTO(turn);
    }


    @Override
    public void deleteById(Integer id) {
        Turn turn = turnRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Turn not found", "id", id));
        turnRepository.delete(turn);
    }


    @Override
    public TurnDTO update(TurnDTO turnDTO, Integer id) {
        Turn turn = turnRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Turn not found", "id", id));

        turn.setDate(turnDTO.getDate());
        Turn turnDB = turnRepository.save(turn);

        //settear paciente y odontologo - crear otro metodo

        return mapDTO(turnDB);
    }

    @Override
    public List<TurnDTO> findAll() {
        List<Turn> turns = turnRepository.findAll();
        List<TurnDTO> turnDTOList = turns.stream().map(turn -> mapDTO(turn)).collect(Collectors.toList());
        return turnDTOList;
    }

    @Override
    public List<TurnDTO> findTurnsBetweenDates(LocalDateTime startDate, Integer numberDays) {
        return null;
    }

    @Override
    public List<TurnDTO> findTurnsNextWeek() {
        List<Turn> turnsDB = turnRepository.findAll();
        Stream<Turn> turnStream = turnsDB.stream().filter(turn -> turn.getDate().isBefore(LocalDateTime.now().plusDays(7)) && turn.getDate().isAfter(LocalDateTime.now()) );
        List<TurnDTO> turnDTOList = turnStream.map(turn -> mapDTO(turn)).collect(Collectors.toList());
        return turnDTOList;
    }


    //-------------------MODEL MAPPER ------------------------
    private TurnDTO mapDTO(Turn turn){
        TurnDTO turnDTO = modelMapper.map(turn, TurnDTO.class);
        return turnDTO;
    }

    private Turn mapEntity(TurnDTO turnDTO){
        Turn turn = modelMapper.map(turnDTO, Turn.class);
        return turn;
    }


}
