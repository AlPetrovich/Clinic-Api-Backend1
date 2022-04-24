package com.dh.clinica.controller.impl;


import com.dh.clinica.controller.CRUDController;
import com.dh.clinica.dto.DentistDTO;
import com.dh.clinica.service.IDentistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/dentist")
public class DentistController implements CRUDController<DentistDTO> {

    @Autowired
    private IDentistService dentistService;


    @Override
    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<DentistDTO> findById(@PathVariable("id") Integer id) {
        DentistDTO dentistDTO = dentistService.findById(id);
        return new ResponseEntity<>(dentistDTO, HttpStatus.OK);
    }

    @Override
    //@CrossOrigin
    @GetMapping("/list")
    public ResponseEntity<List<DentistDTO>> findAll() {
        List<DentistDTO> dentistDTOList = dentistService.findAll();
        return new ResponseEntity<>(dentistDTOList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    //@CrossOrigin
    @PostMapping()
    public ResponseEntity<DentistDTO> create(@Valid @RequestBody DentistDTO dentistDTO) {
        DentistDTO newDentist = dentistService.create(dentistDTO);
        return new ResponseEntity<>(newDentist, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    //@CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @Valid @RequestBody DentistDTO dentistDTO,
            @PathVariable("id") Integer id
    ){
        DentistDTO dentistResponse = dentistService.update(dentistDTO, id);
        return new ResponseEntity<>(dentistResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    //@CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Integer id) {
        dentistService.deleteById(id);
        return new ResponseEntity<>("Dentist removed",HttpStatus.OK);
    }

    @GetMapping("/licence")
    public ResponseEntity<DentistDTO> findByLicence(@RequestParam("licence") String licence){
        DentistDTO dentistDTO = dentistService.findByLicence(licence);
        return new ResponseEntity<>(dentistDTO, HttpStatus.OK);
    }

    @GetMapping("/nameLastname")
    public ResponseEntity<List<DentistDTO>> findByNameAndLastName(@RequestParam("name") String name, @RequestParam("lastname") String lastname){
        List<DentistDTO> dentistDTOList=dentistService.findByNameAndLastname(name, lastname);
        return new ResponseEntity<>(dentistDTOList, HttpStatus.OK);
    }

}
