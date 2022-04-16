package com.dh.clinica.persistence.repositories;
import com.dh.clinica.persistence.entities.Turn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITurnRepository extends JpaRepository<Turn, Integer> {
}
