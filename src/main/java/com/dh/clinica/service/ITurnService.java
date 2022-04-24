package com.dh.clinica.service;
import com.dh.clinica.dto.TurnDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ITurnService extends ICRUDService<TurnDTO>{

    List<TurnDTO> findTurnsBetweenDates(LocalDateTime startDate, Integer numberDays);

    List<TurnDTO> findTurnsNextWeek();

    List<TurnDTO> findByDentistAndPatient(String nameDentist, String namePatient);

}
