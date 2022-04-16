package com.dh.clinica.persistence.repositories.auth;
import com.dh.clinica.persistence.entities.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {

    public Optional<User>  findByMail(String mail);

    public Optional<User>  findByUsernameOrMail(String username, String email);

    public Optional<User>  findByUsername(String username);

    public Boolean existsByUsername(String username);

    public Boolean existsByMail(String mail);

}
