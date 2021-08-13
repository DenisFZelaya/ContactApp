package com.umhdevmovil.contactapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.umhdevmovil.contactapp.Models.LoginModel;
import com.umhdevmovil.contactapp.Models.UsuariosModel;

public class MainActivity extends AppCompatActivity {

    EditText Email, Contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        System.out.println("Hola desde el main activity");
        DatabaseHelper dataBaseHelper = new DatabaseHelper(MainActivity.this);
        //dataBaseHelper = new DatabaseHelper(this);


        Email = findViewById(R.id.inputNombreApellido);
        Contrasena = findViewById(R.id.inputContrasenaLoginPage);

    }



    public  void IniciarSesion(View v){
        LoginModel userLogin = new LoginModel();

        //Guardar Email
        if(!Email.getText().toString().equals("")){
            userLogin.setEmail(Email.getText().toString());
            Email.setBackgroundColor(Color.WHITE);
        } else {
            Email.setBackgroundColor(Color.parseColor("#ffb6c1"));
        }

        if(!Contrasena.getText().toString().equals("")){
            userLogin.setContrasena(Contrasena.getText().toString());
            Contrasena.setBackgroundColor(Color.WHITE);
        } else {
            Contrasena.setBackgroundColor(Color.parseColor("#ffb6c1"));
        }

        if(!Email.getText().toString().equals("") && !Contrasena.getText().toString().equals("")){
            try {
                DatabaseHelper db = new DatabaseHelper(this);
                UsuariosModel usuarioEncontrado = db.getUsuarioLogin(userLogin);

                if(usuarioEncontrado.getNombre() != "null"){
                    Bundle datoenvia = new Bundle();
                    datoenvia.putInt ("idUsuario",usuarioEncontrado.getId());

                    Intent menuUsuario = new Intent (MainActivity.this, MenuUsuario.class);
                    menuUsuario.putExtras(datoenvia);
                    startActivity(menuUsuario);
                    //finish(); eliminar el activity actual
                }else if(usuarioEncontrado.getNombre() == "null") {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Usuario no existe o credenciales incorrectas", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }catch (Exception ex) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Error encontrado: " + ex.getMessage())
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });

                builder.show();

                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), ex.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
    }

    public void Registro (View v){
        Intent RegistroPage = new Intent (MainActivity.this, Registro.class);
        startActivity(RegistroPage);
    }
}