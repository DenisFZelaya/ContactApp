package com.umhdevmovil.contactapp.Models;

public class LoginModel {

    private String Email;
    private String Contrasena;

    //Constructor
    public LoginModel(String Email, String Contrasena) {
        this.Email = Email;
        this.Contrasena = Contrasena;
    }

    public LoginModel() {
    }

    public String getEmail() {
        return Email;
    }
    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getContrasena() {
        return Contrasena;
    }
    public void setContrasena(String Contrasena) {
        this.Contrasena = Contrasena;
    }
}
