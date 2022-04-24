package com.dh.clinica.controller.impl;
import com.dh.clinica.controller.CRUDController;
import com.dh.clinica.dto.TurnDTO;
import com.dh.clinica.service.impl.TurnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/turn")
public class TurnController implements CRUDController<TurnDTO> {

    @Autowired
    private TurnService turnService;


    @Override
    @GetMapping("/{id}")
    public ResponseEntity<TurnDTO> findById(@PathVariable("id") Integer id) {
        TurnDTO turnDTO= turnService.findById(id);
        return ResponseEntity.ok(turnDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    @PostMapping()
    public ResponseEntity<TurnDTO> create(@Valid @RequestBody TurnDTO turnDTO) {
        TurnDTO saveTurnDTO= turnService.create(turnDTO);
        return ResponseEntity.ok(saveTurnDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(TurnDTO turnDTO, Integer id) {
        TurnDTO updateTurnDTO= turnService.update(turnDTO, id);
        return ResponseEntity.ok(updateTurnDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(Integer id) {
        turnService.deleteById(id);
        return ResponseEntity.ok("Turn removed");
    }

    @Override
    @GetMapping("list")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(turnService.findAll());
    }

    //Turnos proximos dias
    @GetMapping("/next-week")
    public ResponseEntity<?> findNextWeek(){
        List<TurnDTO> turns= turnService.findTurnsNextWeek();
        if (turns.size()==0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Turns not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(turns);
    }

    @GetMapping("/dentist/patient")
    public ResponseEntity<List<TurnDTO>> findTurnsByDentistAndPatient(@RequestParam("dentist") String nameDentist, @RequestParam("patient") String namePatient){
        List<TurnDTO> turns= turnService.findByDentistAndPatient(nameDentist, namePatient);
        if (turns.size()==0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(turns);
    }

}
