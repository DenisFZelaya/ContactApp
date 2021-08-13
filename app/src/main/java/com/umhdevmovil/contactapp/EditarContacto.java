package com.umhdevmovil.contactapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.umhdevmovil.contactapp.Models.ContactosAdapter;
import com.umhdevmovil.contactapp.Models.ContactosModel;

import java.util.ArrayList;

public class EditarContacto extends AppCompatActivity {
    private EditText Nombre,Apellido, Email, Telefono, Genero;
    private  int idContacto;
    private RadioButton Hombre, Mujer;

    ContactosModel editarContacto = new ContactosModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_contacto);

        //Recibir el idContacto que se pasa desde ContactosAdaper
        Bundle recibirIdContacto= this.getIntent().getExtras();
        if (recibirIdContacto !=null) {
            idContacto = recibirIdContacto.getInt("IdContacto");
        }
        //Unir codigo java con el xml
        Nombre = findViewById(R.id.inputNombreEContacto);
        Apellido = findViewById(R.id.inputApellidoEContacto);
        Email = findViewById(R.id.inputNombreApellidoEContacto);
        Telefono = findViewById(R.id.inputTelefonoEContacto);
        Hombre = findViewById(R.id.radio_button_HombreEContacto);
        Mujer = findViewById(R.id.radio_button_MujerEContacto);

        //Traer datos desde la db y guardarla en modelo
        DatabaseHelper db = new DatabaseHelper(EditarContacto.this);

        editarContacto = db.getContactoModelById(idContacto);

        Nombre.setText(editarContacto.getNombre());
        Apellido.setText(editarContacto.getApellido());
        Email.setText(editarContacto.getEmail());
        Telefono.setText(editarContacto.getTelefono());

        if(editarContacto.getGenero().equals("Masculino")){
            Hombre.setChecked(true);
        }else{
            Mujer.setChecked(false);
        }
    }

    public void actualizarContacto(View v){

        System.out.println("actualizarContacto: Nombre: "+editarContacto.getNombre());
        System.out.println("actualizarContacto: Apellido: "+editarContacto.getApellido());
        System.out.println("actualizarContacto: Email: "+editarContacto.getEmail());
        System.out.println("actualizarContacto: Genero: "+editarContacto.getGenero());
        System.out.println("actualizarContacto: Telefono: "+editarContacto.getTelefono());
        System.out.println("actualizarContacto: IdUsuario: "+editarContacto.getIdUsuario());

        //Validar Nombre
        if(!Nombre.getText().toString().equals("")){
            editarContacto.setNombre(Nombre.getText().toString());
            System.out.println("SetNombreEditarContacto" + editarContacto.getNombre());
            Nombre.setBackgroundColor(Color.WHITE);
        }else{
            Nombre.setBackgroundColor(Color.parseColor("#ffb6c1"));
        }

        //Validar Apellido
        if(!Apellido.getText().toString().equals("")){
            editarContacto.setApellido(Apellido.getText().toString());
            Apellido.setBackgroundColor(Color.WHITE);
        }else{
            Apellido.setBackgroundColor(Color.parseColor("#ffb6c1"));
        }

        //Validar Telefono
        if(!Telefono.getText().toString().equals("") || Telefono.getText().toString().length() > 8){
            editarContacto.setTelefono(Telefono.getText().toString());
            Telefono.setBackgroundColor(Color.WHITE);
        }else{
            Telefono.setBackgroundColor(Color.parseColor("#ffb6c1"));
        }

        //Validar Email
        if(!Email.getText().toString().equals("")){
            editarContacto.setEmail(Email.getText().toString());
            Email.setBackgroundColor(Color.WHITE);
        }else{
            Email.setBackgroundColor(Color.parseColor("#ffb6c1"));
        }

        //Insertar Genero
        if(Hombre.isChecked()){
            editarContacto.setGenero("Masculino");
        }else if(Mujer.isChecked()){
            editarContacto.setGenero("Femenino");
        }


        if(
                editarContacto.getNombre() != null && editarContacto.getNombre() != "" &&
                        editarContacto.getApellido() != null && editarContacto.getApellido() != "" &&
                        editarContacto.getEmail() != null && editarContacto.getEmail() != "" &&
                        editarContacto.getGenero() != null && editarContacto.getGenero() != "" && editarContacto.getTelefono() != null && editarContacto.getTelefono() != ""
        ){

            DatabaseHelper db = new DatabaseHelper(EditarContacto.this);
            boolean resultado = db.EditarContacto(editarContacto);

            if(resultado){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Su contacto ha sido actualizado con exito!")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                onBackPressed();
                            }
                        });
                builder.show();
            }else {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "No se ha podido actualizar el contacto", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
    }

    public void eliminarcontacto(View v){
        DatabaseHelper db = new DatabaseHelper(EditarContacto.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Desea eliminar el contacto!")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        boolean resultado = db.deleteContactoById(editarContacto.getId());

                        if(resultado){
                            Snackbar snackbarSuc= Snackbar.make(findViewById(android.R.id.content), "Se ha eliminado el contacto", Snackbar.LENGTH_LONG);
                            snackbarSuc.show();
                            onBackPressed();
                        }else {
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "No se ha podido eliminar", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }


                    }
                });
        builder.show();



    }

}