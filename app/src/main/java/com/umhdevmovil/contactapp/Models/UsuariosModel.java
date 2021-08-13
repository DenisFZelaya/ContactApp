package com.umhdevmovil.contactapp.Models;

public class UsuariosModel {
    private  int Id;
    private String Nombre;
    private String Apellido;
    private String Genero;
    private String Email;
    private String Telefono;
    private String Contrasena;

    //Constructor
    public UsuariosModel(int Id, String Nombre, String Apellido, String Genero, String Email, String Telefono, String Contrasena) {
        this.Id = Id;
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.Genero = Genero;
        this.Email = Email;
        this.Telefono = Telefono;
        this.Contrasena = Contrasena;
    }

    public UsuariosModel() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellido() {
        return Apellido;
    }
    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    public String getGenero() {
        return Genero;
    }
    public void setGenero(String Genero) {
        this.Genero = Genero;
    }

    public String getEmail() {
        return Email;
    }
    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getTelefono() {
        return Telefono;
    }
    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getContrasena() {
        return Contrasena;
    }
    public void setContrasena(String Contrasena) {
        this.Contrasena = Contrasena;
    }
}
