package com.dh.clinica.dto.auth;


public class RegisterDTO {

    private String name;
    private String username;
    private String mail;
    private String password;

    public RegisterDTO() {
    }

    public RegisterDTO(String name, String username, String mail, String password) {
        this.name = name;
        this.username = username;
        this.mail = mail;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
