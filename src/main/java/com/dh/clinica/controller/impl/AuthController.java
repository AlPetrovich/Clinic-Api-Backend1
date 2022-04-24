package com.dh.clinica.controller.impl;
import com.dh.clinica.dto.auth.LoginDTO;
import com.dh.clinica.dto.auth.RegisterDTO;
import com.dh.clinica.persistence.entities.auth.Role;
import com.dh.clinica.persistence.entities.auth.User;
import com.dh.clinica.persistence.repositories.auth.IRoleRepository;
import com.dh.clinica.persistence.repositories.auth.IUserRepository;
import com.dh.clinica.security.JWTAuthResponseDTO;
import com.dh.clinica.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    //Ya fue registrado como Bean
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    //El login se lo realiza con el username y password que se encuentran registrados en la base de datos
    //si no existe enviara "bad credentials"
    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO) {
        //autenticar usuario - ver privilegios
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrMail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication); //establecer el contexto de seguridad

        //obtener el token del jwtprovider
        String token = jwtTokenProvider.generateToken(authentication);


        return ResponseEntity.ok(new JWTAuthResponseDTO(token));
    }

    //Metodo Registrar
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO) {
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            return new ResponseEntity<>("Fail -> Username is already taken!", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByMail(registerDTO.getMail())) {
            return new ResponseEntity<>("Fail -> Email is already in use!", HttpStatus.BAD_REQUEST);
        }
        //No se encuentra registrado - entonces se registra
        User user = new User();
        user.setName(registerDTO.getName());
        user.setUsername(registerDTO.getUsername());
        user.setMail(registerDTO.getMail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword())); //encriptar password

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles)); //agregar rol - singleton -> solo un elemento (rol)

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }

}
