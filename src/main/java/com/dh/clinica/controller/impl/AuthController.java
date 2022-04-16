package com.dh.clinica.controller.impl;
import com.dh.clinica.dto.auth.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    //El login se lo realiza con el username y password que se encuentran registrados en la base de datos
    //si no existe enviara "bad credentials"

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDTO) {
        //autenticar usuario - ver privilegios
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrMail(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication); //establecer el contexto de seguridad
        return new ResponseEntity<>("You have successfully logged in", HttpStatus.OK);
    }


}
