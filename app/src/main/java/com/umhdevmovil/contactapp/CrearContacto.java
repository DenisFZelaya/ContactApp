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
import com.umhdevmovil.contactapp.Models.ContactosModel;
import com.umhdevmovil.contactapp.Models.UsuariosModel;

public class CrearContacto extends AppCompatActivity {

    private EditText Nombre,Apellido, Email, Telefono, Genero;
    private  int idUsuario;
    private RadioButton Hombre, Mujer;
    private Button btnCrearContacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_contacto);

        Bundle recibirIdUsuario= this.getIntent().getExtras();
        if (recibirIdUsuario !=null) {
            idUsuario = recibirIdUsuario.getInt("idUsuario");
        }

        Nombre = findViewById(R.id.inputNombreNContacto);
        Apellido = findViewById(R.id.inputApellidoNContacto);
        Email = findViewById(R.id.inputNombreApellidoNContacto);
        Telefono = findViewById(R.id.inputTelefonoNContacto);
        Hombre = findViewById(R.id.radio_button_HombreNContacto);
        Mujer = findViewById(R.id.radio_button_MujerNContacto);

        Hombre.setChecked(true);
    }

    public void crearContacto(View v){
        ContactosModel nuevoContacto = new ContactosModel();

        //IdUsuario
        nuevoContacto.setIdUsuario(idUsuario);

        //Validar Nombre
        if(!Nombre.getText().toString().equals("")){
            nuevoContacto.setNombre(Nombre.getText().toString());
            Nombre.setBackgroundColor(Color.WHITE);
        }else{
            Nombre.setBackgroundColor(Color.parseColor("#ffb6c1"));
        }

        //Validar Apellido
        if(!Apellido.getText().toString().equals("")){
            nuevoContacto.setApellido(Apellido.getText().toString());
            Apellido.setBackgroundColor(Color.WHITE);
        }else{
            Apellido.setBackgroundColor(Color.parseColor("#ffb6c1"));
        }

        //Validar Telefono
        if(!Telefono.getText().toString().equals("") || Telefono.getText().toString().length() > 8){
            nuevoContacto.setTelefono(Telefono.getText().toString());
            Telefono.setBackgroundColor(Color.WHITE);
        }else{
            Telefono.setBackgroundColor(Color.parseColor("#ffb6c1"));
        }

        //Validar Email
        if(!Email.getText().toString().equals("")){
            nuevoContacto.setEmail(Email.getText().toString());
            Email.setBackgroundColor(Color.WHITE);
        }else{
            Email.setBackgroundColor(Color.parseColor("#ffb6c1"));
        }


        //Insertar Genero
        if(Hombre.isChecked()){
            nuevoContacto.setGenero("Masculino");
        }else if(Mujer.isChecked()){
            nuevoContacto.setGenero("Femenino");
        }

        System.out.println("nuevoConctaco: Nombre: "+nuevoContacto.getNombre());
        System.out.println("nuevoConctaco: Apellido: "+nuevoContacto.getApellido());
        System.out.println("nuevoConctaco: Email: "+nuevoContacto.getEmail());
        System.out.println("nuevoConctaco: Genero: "+nuevoContacto.getGenero());
        System.out.println("nuevoConctaco: Telefono: "+nuevoContacto.getTelefono());
        System.out.println("nuevoConctaco: IdUsuario: "+nuevoContacto.getIdUsuario());


        if(
                nuevoContacto.getNombre() != null && nuevoContacto.getNombre() != "" &&
                        nuevoContacto.getApellido() != null && nuevoContacto.getApellido() != "" &&
                        nuevoContacto.getEmail() != null && nuevoContacto.getEmail() != "" &&
                        nuevoContacto.getGenero() != null && nuevoContacto.getGenero() != "" &&
                        nuevoContacto.getTelefono() != null && nuevoContacto.getTelefono() != ""
        ){
            System.out.println("Datos listos para guardar");
            DatabaseHelper db = new DatabaseHelper(CrearContacto.this);
            boolean resultado = db.InsertarContacto(nuevoContacto);

            if(resultado){

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Su contacto ha sido creado con exito!")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                onBackPressed();
                            }
                        });
                builder.show();
            }else {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "No se ha podido crear el contacto", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
    }
}