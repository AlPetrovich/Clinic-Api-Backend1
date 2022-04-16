package com.dh.clinica.persistence.repositories;
import com.dh.clinica.persistence.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAddressRepository extends JpaRepository<Address, Integer>{


    
}
