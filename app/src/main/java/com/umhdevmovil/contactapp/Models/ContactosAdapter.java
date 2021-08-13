package com.umhdevmovil.contactapp.Models;

import java.util.ArrayList;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.RestrictionEntry;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.umhdevmovil.contactapp.CrearContacto;
import com.umhdevmovil.contactapp.EditarContacto;
import com.umhdevmovil.contactapp.MenuUsuario;
import com.umhdevmovil.contactapp.R;

import static androidx.core.content.ContextCompat.startActivity;

public class ContactosAdapter extends ArrayAdapter<ContactosModel> {
    public ContactosAdapter(Context context, ArrayList<ContactosModel> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_contacto, parent, false);
        }

        // Get the data item for this position
        ContactosModel user = getItem(position);

        // Lookup view for data population
        TextView textViewNombreItem = (TextView) convertView.findViewById(R.id.textViewNombreItem);
        TextView textViewEmailItem = (TextView) convertView.findViewById(R.id.textViewEmailItem);
        TextView textViewTelefonoItem = (TextView) convertView.findViewById(R.id.textViewTelefonoItem);
        Button btnItem = (Button) convertView.findViewById(R.id.btnEditarContactoItem);
        // Populate the data into the template view using the data object
        textViewNombreItem.setText(user.getNombre() + " " + user.getApellido());
        textViewEmailItem.setText(user.getEmail());
        textViewTelefonoItem.setText(user.getTelefono());
        // Return the completed view to render on screen

        btnItem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                cargarPageEditar(user.getId());
            }
        });

        return convertView;
    }

    protected void cargarPageEditar(int idContacto){
        try{
            System.out.println("Funcion editar contacto: " + idContacto);
            Bundle datoenvia = new Bundle();
            datoenvia.putInt ("IdContacto",idContacto);

            Intent intentEditarContacto = new Intent (getContext(), EditarContacto.class);
            intentEditarContacto.putExtras(datoenvia);
            getContext().startActivity(intentEditarContacto);
        }catch (Exception ex){
            System.out.println("Error cargando EditarContacto: " + ex.getMessage());
        }

    }

}
