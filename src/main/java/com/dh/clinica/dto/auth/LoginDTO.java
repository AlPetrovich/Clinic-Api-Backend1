package com.dh.clinica.dto.auth;

public class LoginDTO {

    private String usernameOrMail;
    private String password;

    public String getUsernameOrMail() {
        return usernameOrMail;
    }

    public void setUsernameOrMail(String usernameOrMail) {
        this.usernameOrMail = usernameOrMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
