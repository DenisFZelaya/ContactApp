package com.umhdevmovil.contactapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.android.material.snackbar.Snackbar;
import com.umhdevmovil.contactapp.Models.UsuariosModel;

public class Registro extends AppCompatActivity {

    private EditText Nombre,Apellido, Email, Telefono, Genero, Contrasena, RepeatContrasena;
    private RadioButton Hombre, Mujer;
    private Button btnCrearUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        setTitle("Registro");
        getSupportActionBar().hide();

        Nombre = findViewById(R.id.inputNombre);
        Apellido = findViewById(R.id.inputApellido);
        Email = findViewById(R.id.inputNombreApellido);
        Telefono = findViewById(R.id.inputTelefono);
        Hombre = findViewById(R.id.radio_button_Hombre);
        Mujer = findViewById(R.id.radio_button_Mujer);
        Contrasena = findViewById(R.id.inputContrasenaLoginPage);
        RepeatContrasena = findViewById(R.id.inputRepeatContra);
    }

    public void crearUsuario(View v){
        UsuariosModel nuevoUsuario = new UsuariosModel();

        //Validar Nombre
        if(!Nombre.getText().toString().equals("")){
            nuevoUsuario.setNombre(Nombre.getText().toString());
            Nombre.setBackgroundColor(Color.WHITE);
        }else{
            Nombre.setBackgroundColor(Color.parseColor("#ffb6c1"));
        }

        //Validar Apellido
        if(!Apellido.getText().toString().equals("")){
            nuevoUsuario.setApellido(Apellido.getText().toString());
            Apellido.setBackgroundColor(Color.WHITE);
        }else{
            Apellido.setBackgroundColor(Color.parseColor("#ffb6c1"));
        }

        //Validar Telefono
        if(!Telefono.getText().toString().equals("") || Telefono.getText().toString().length() > 8){
            nuevoUsuario.setTelefono(Telefono.getText().toString());
            Telefono.setBackgroundColor(Color.WHITE);
        }else{
            Telefono.setBackgroundColor(Color.parseColor("#ffb6c1"));
        }

        //Validar Email
        if(!Email.getText().toString().equals("")){
            nuevoUsuario.setEmail(Email.getText().toString());
            Email.setBackgroundColor(Color.WHITE);
        }else{
            Email.setBackgroundColor(Color.parseColor("#ffb6c1"));
        }


        //Insertar Genero
        if(Hombre.isChecked()){
            nuevoUsuario.setGenero("Masculino");
        }else if(Mujer.isChecked()){
            nuevoUsuario.setGenero("Femenino");
        }

        //Validar contraseña
        if(Contrasena.getText().toString().equals(RepeatContrasena.getText().toString())){
            if(Contrasena.getText().toString().length() > 0 && RepeatContrasena.getText().toString().length() > 0){

                Contrasena.setBackgroundColor(Color.WHITE);
                RepeatContrasena.setBackgroundColor(Color.WHITE);
                nuevoUsuario.setContrasena(Contrasena.getText().toString());

            } else {
                Contrasena.setBackgroundColor(Color.parseColor("#ffb6c1"));
                RepeatContrasena.setBackgroundColor(Color.parseColor("#ffb6c1"));

                System.out.println("Contrasena " + Contrasena.getText().toString());
                System.out.println("ContrasenaRepeat " + RepeatContrasena.getText().toString());
            }
        }

        if(
                nuevoUsuario.getNombre() != null && nuevoUsuario.getNombre() != "" &&
                nuevoUsuario.getApellido() != null && nuevoUsuario.getApellido() != "" &&
                nuevoUsuario.getEmail() != null && nuevoUsuario.getEmail() != "" &&
                nuevoUsuario.getGenero() != null && nuevoUsuario.getGenero() != "" &&
                nuevoUsuario.getTelefono() != null && nuevoUsuario.getTelefono() != "" &&
                nuevoUsuario.getContrasena() != null && nuevoUsuario.getContrasena() != ""
        ){
            System.out.println("Datos listos para guardar");
            DatabaseHelper db = new DatabaseHelper(Registro.this);

            boolean resultado = db.InsertarUsuario(nuevoUsuario);

            if(resultado){

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Su usuario ha sido creado, será redirigido al login")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                onBackPressed();
                            }
                        });

                builder.show();

            }else {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "No se ha podido crear su usuario", Snackbar.LENGTH_LONG);
                snackbar.show();
            }






        }


    }
}