package com.dh.clinica.persistence.repositories;
import com.dh.clinica.persistence.entities.Turn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ITurnRepository extends JpaRepository<Turn, Integer> {

    @Query("SELECT t FROM Turn t WHERE t.dentist.name = ?1 AND t.patient.name = ?2")
    Optional<List<Turn>> findByDentistAndPatient(String nameDentist, String namePatient);


}
