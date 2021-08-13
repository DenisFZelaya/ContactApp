package com.umhdevmovil.contactapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.umhdevmovil.contactapp.Models.ContactosAdapter;
import com.umhdevmovil.contactapp.Models.ContactosModel;
import com.umhdevmovil.contactapp.Models.UsuariosModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MenuUsuario extends AppCompatActivity {

    //
    int id;
    //
    EditText NombreApellido, inputEmailMenuUsuario;

    ListView lv_contactoView;
    ArrayAdapter contactoArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_usuario);

        DatabaseHelper db = new DatabaseHelper(MenuUsuario.this);
        UsuariosModel usuarioActual = new UsuariosModel();

            Bundle recibirIdUsuario= this.getIntent().getExtras();
            if (recibirIdUsuario !=null) {
                int id = recibirIdUsuario.getInt("idUsuario");
                //Llenar una instancia del modelo
                usuarioActual = db.getUsuarioModelById(id);
            }


            lv_contactoView = findViewById(R.id.lv_contactosView);

            NombreApellido = findViewById(R.id.inputNombreApellido);
            inputEmailMenuUsuario = findViewById(R.id.inputEmailMenuUsuario);
            NombreApellido.setText(usuarioActual.getNombre() + " " + usuarioActual.getApellido());
            inputEmailMenuUsuario.setText(usuarioActual.getEmail());
            id=usuarioActual.getId();

        // Construct the data source
        ArrayList<ContactosModel> arrayOfContactos = (ArrayList<ContactosModel>) db.getAllContactos(id);
        // Create the adapter to convert the array to views
        ContactosAdapter adapter = new ContactosAdapter(this, arrayOfContactos);
        // Attach the adapter to a ListView
        lv_contactoView.setAdapter(adapter);

        System.out.println("UsuarioActual: Nombre: " + usuarioActual.getNombre());
        System.out.println("UsuarioActual: Apellido: " + usuarioActual.getApellido());
        //ShowCustomerOnListView(db);
    }

    public  void irCrearContacto(View v){
        Bundle datoenvia = new Bundle();
        datoenvia.putInt ("idUsuario",id);

        Intent intentCrearConctacto = new Intent (MenuUsuario.this, CrearContacto.class);
        intentCrearConctacto.putExtras(datoenvia);
        startActivity(intentCrearConctacto);

    }

    public void RecargarListaContactos(View v){
            ListView lv_contactoView = findViewById(R.id.lv_contactosView);
            DatabaseHelper db = new DatabaseHelper(this);
            ArrayList<ContactosModel> arrayOfContactos = (ArrayList<ContactosModel>) db.getAllContactos(id);
            ContactosAdapter adapter = new ContactosAdapter(this, arrayOfContactos);
            lv_contactoView.setAdapter(adapter);
    }
}