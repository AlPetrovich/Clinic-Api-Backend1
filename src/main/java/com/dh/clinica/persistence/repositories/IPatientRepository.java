package com.dh.clinica.persistence.repositories;
import com.dh.clinica.persistence.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPatientRepository extends JpaRepository<Patient, Integer> {
}
