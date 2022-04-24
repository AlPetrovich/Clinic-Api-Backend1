package com.dh.clinica.persistence.repositories;
import com.dh.clinica.persistence.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAddressRepository extends JpaRepository<Address, Integer>{


    //buscar por localidad
    public Optional<List<Address>> findByLocation(String location);
    
}
