package com.dh.clinica.security;

import com.dh.clinica.exceptions.ClinicAppException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {
        //obtenemos el usuario del token
        String username = authentication.getName();
        //fecha actual - fecha de expiracion
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        //establecemos los datos al token
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        return token;
    }

    public String getUsernameFromJWT(String token) {
        //Claims son los datos del token
        //getSubject() obtiene el usuario del token
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //Autenticar el token
    public boolean authToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        }catch (SignatureException e) {
            throw new ClinicAppException(HttpStatus.BAD_REQUEST, "Invalid JWT signature");
        }catch (MalformedJwtException e) {
            throw new ClinicAppException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        }catch (ExpiredJwtException e) {
            throw new ClinicAppException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        }catch(UnsupportedJwtException e) {
            throw new ClinicAppException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        }catch (IllegalArgumentException e) {
            throw new ClinicAppException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
        }
    }
}
