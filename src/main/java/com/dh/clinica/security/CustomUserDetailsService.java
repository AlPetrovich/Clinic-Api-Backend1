package com.dh.clinica.security;
import com.dh.clinica.persistence.entities.auth.Role;
import com.dh.clinica.persistence.entities.auth.User;
import com.dh.clinica.persistence.repositories.auth.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    //metodo carga el usuario por su username / mail
    @Override
    public UserDetails loadUserByUsername(String usernameOrMail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrMail(usernameOrMail, usernameOrMail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        //usuario que retornamos -> username, password, roles asociados
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRoles(user.getRoles()));
    }


    private Collection<? extends GrantedAuthority> mapRoles(Set<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
