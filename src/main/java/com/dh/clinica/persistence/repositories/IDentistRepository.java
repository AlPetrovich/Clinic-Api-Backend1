package com.dh.clinica.persistence.repositories;
import com.dh.clinica.persistence.entities.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IDentistRepository extends JpaRepository<Dentist, Integer> {

    @Query("SELECT d FROM Dentist d WHERE d.name=?1 AND d.lastname=?2")
    Optional<List<Dentist>> findByNameAndLastname(String name, String lastname);

    Optional<Dentist> findByLicence(String licence);

}
