package com.dh.clinica.persistence.repositories;
import com.dh.clinica.persistence.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPatientRepository extends JpaRepository<Patient, Integer> {

    Optional<List<Patient>> findByName(String name);

    Optional<Patient> findByDni(String dni);

}
