package com.dh.clinica.security;

public class JWTAuthResponseDTO {

    private String tokenAccess;
    private String typeToken = "Bearer";

    public JWTAuthResponseDTO(String tokenAccess, String typeToken) {
        this.tokenAccess = tokenAccess;
        this.typeToken = typeToken;
    }

    public JWTAuthResponseDTO(String tokenAccess) {
        this.tokenAccess = tokenAccess;
    }

    public JWTAuthResponseDTO() {
    }

    public String getTokenAccess() {
        return tokenAccess;
    }

    public void setTokenAccess(String tokenAccess) {
        this.tokenAccess = tokenAccess;
    }

    public String getTypeToken() {
        return typeToken;
    }

    public void setTypeToken(String typeToken) {
        this.typeToken = typeToken;
    }
}
