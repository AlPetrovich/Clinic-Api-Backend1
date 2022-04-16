package com.dh.clinica.persistence.repositories.auth;
import com.dh.clinica.persistence.entities.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {

    public Optional<Role> findByName(String name);
}
