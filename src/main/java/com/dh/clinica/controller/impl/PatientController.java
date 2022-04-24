package com.dh.clinica.controller.impl;
import com.dh.clinica.controller.CRUDController;
import com.dh.clinica.dto.PatientDTO;
import com.dh.clinica.service.impl.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/patient")
public class PatientController implements CRUDController<PatientDTO> {

    @Autowired
    private PatientService patientService;


    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> findById(@PathVariable("id") Integer id) {
            PatientDTO patientDTO= patientService.findById(id);
            return new ResponseEntity<>(patientDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    @PostMapping()
    public ResponseEntity<PatientDTO> create(@Valid @RequestBody PatientDTO patientDTO) {
        PatientDTO responsePatientDTO= patientService.create(patientDTO);
        return new ResponseEntity<>(responsePatientDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @Valid @RequestBody PatientDTO patientDTO,
            @PathVariable("id") Integer id)
    {
        PatientDTO responsePatientDTO= patientService.update(patientDTO, id);
        return new ResponseEntity<>(responsePatientDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Integer id) {
        patientService.deleteById(id);
        return new ResponseEntity<>("Patient removed", HttpStatus.OK);
    }

    @Override
    @GetMapping("/list")
    public ResponseEntity<List<PatientDTO>> findAll() {
        return new ResponseEntity<>(patientService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/dni")
    public ResponseEntity<PatientDTO> findByDni(@RequestParam String dni) {
        PatientDTO patientDTO= patientService.findByDni(dni);
        return new ResponseEntity<>(patientDTO, HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<List<PatientDTO>> findByName(@RequestParam String name) {
        return new ResponseEntity<>(patientService.findByName(name), HttpStatus.OK);
    }
}
