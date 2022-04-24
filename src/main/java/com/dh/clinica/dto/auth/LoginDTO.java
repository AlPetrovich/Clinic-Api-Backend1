package com.dh.clinica.dto.auth;

public class LoginDTO {

    private String usernameOrMail;
    private String password;

    public LoginDTO() {
    }

    public LoginDTO(String usernameOrMail, String password) {
        this.usernameOrMail = usernameOrMail;
        this.password = password;
    }

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
