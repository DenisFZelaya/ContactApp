package com.umhdevmovil.contactapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.umhdevmovil.contactapp.Models.ContactosModel;
import com.umhdevmovil.contactapp.Models.LoginModel;
import com.umhdevmovil.contactapp.Models.UsuariosModel;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, "dbcontactos.db", null, 1);
        System.out.println("Creando contructor de DatabaseHelper");
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Sentencia SQL para crear la tabla Usuario
        String createTableUsuarios = "" +
                "        CREATE TABLE Usuario (" +
                "                IdUsuario INTEGER PRIMARY KEY AUTOINCREMENT," +
                "                Nombre TEXT," +
                "                Apellido TEXT," +
                "                Email TEXT," +
                "                Genero TEXT," +
                "                Telefono TEXT," +
                "                Contrasena TEXT" +
                ");";

        //Sentencia SQL para crear la tabla Contacto
        String createTableContactos = "" +
                "        CREATE TABLE Contactos (" +
                "                IdContacto INTEGER PRIMARY KEY AUTOINCREMENT," +
                "                Nombre TEXT," +
                "                Apellido TEXT," +
                "                Email TEXT," +
                "                Genero TEXT," +
                "                Telefono TEXT," +
                "                idUsuario INTEGER" +
                ");";

        db.execSQL(createTableUsuarios);
        db.execSQL(createTableContactos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //Método para obtener un listado de los contactos - LISTO
    //Genera un arreglo de clases
    public List<ContactosModel> getAllContactos(int id){
        List<ContactosModel> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM Contactos WHERE idUsuario =" + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do {
                int idContacto = cursor.getInt(0);
                String nombreContacto = cursor.getString(1);
                String apellidoContacto = cursor.getString(2);
                String generoContacto = cursor.getString(3);
                String emailContacto = cursor.getString(4);
                String telefonoContacto = cursor.getString(5);
                int idUsuario = cursor.getInt(6);

                ContactosModel contactoModel = new ContactosModel(idContacto, nombreContacto, apellidoContacto, generoContacto, emailContacto,telefonoContacto,idUsuario );
                returnList.add(contactoModel);
            }while (cursor.moveToNext());
        } else {

        }
        //cursor.close();
        //db.close();
        return returnList;
    };

    //Metodo para traer contacto por ID
    public ContactosModel getContactoModelById(int id){
        ContactosModel contactoSelected = new ContactosModel();

        String queryString = "SELECT * FROM Contactos WHERE IdContacto = " + id + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do {
                contactoSelected.setId(cursor.getInt(0));
                contactoSelected.setNombre(cursor.getString(1));
                contactoSelected.setApellido(cursor.getString(2));
                contactoSelected.setEmail(cursor.getString(3));
                contactoSelected.setGenero(cursor.getString(4));
                contactoSelected.setTelefono(cursor.getString(5));
                contactoSelected.setIdUsuario(cursor.getInt(6));
            }while (cursor.moveToNext());
        } else {
            contactoSelected.setNombre("null");
        }
        cursor.close();
        db.close();
        return contactoSelected;
    };

    //Método para crear contacto
    public boolean InsertarContacto(ContactosModel contactosModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("IdUsuario", (Integer) null);
        cv.put("Nombre", contactosModel.getNombre());
        cv.put("Apellido", contactosModel.getApellido());
        cv.put("Email", contactosModel.getEmail());
        cv.put("Genero", contactosModel.getGenero());
        cv.put("Telefono", contactosModel.getTelefono());
        cv.put("idUsuario", contactosModel.getIdUsuario());

        long insert = db.insert("Contactos", null, cv);
        if(insert == -1){
            return  false;
        }else{
            return  true;
        }
    }

    //Metodo para editar contacto
    public boolean EditarContacto(ContactosModel contatoEditar){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "UPDATE Contactos " +
                "SET Nombre = '" + contatoEditar.getNombre() + "', "+
                " Apellido = '" + contatoEditar.getApellido() + "', "+
                " Email = '" + contatoEditar.getEmail() + "', "+
                " Genero = '" + contatoEditar.getGenero() + "', "+
                " Telefono = '" + contatoEditar.getTelefono() + "' "+
                " WHERE IdContacto = " + contatoEditar.getId() + ";";

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            return  false;
        }else  {
            return  true;
        }
    }

    //Método para eliminar contacto
    public boolean deleteContactoById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM Contactos WHERE IdContacto = " + id;

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            return  false;
        }else  {
            return  true;
        }
    }

    //Metodo para crear un Usuario
    public boolean InsertarUsuario(UsuariosModel usuariosModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("IdUsuario", (Integer) null);
        cv.put("Nombre", usuariosModel.getNombre());
        cv.put("Apellido", usuariosModel.getApellido());
        cv.put("Email", usuariosModel.getEmail());
        cv.put("Genero", usuariosModel.getGenero());
        cv.put("Telefono", usuariosModel.getTelefono());
        cv.put("Contrasena", usuariosModel.getContrasena());

        long insert = db.insert("Usuario", null, cv);
        if(insert == -1){
            return  false;
        }else{
            return  true;
        }
    }

    //----------------------------------------------------------------------
    //Método de login , retornar el id del usuario encontrado
    public UsuariosModel getUsuarioLogin(LoginModel login){
        UsuariosModel usuarioSelected = new UsuariosModel();

        String queryString = "SELECT * FROM Usuario WHERE Email = '" + login.getEmail() + "' AND Contrasena = '" + login.getContrasena() + "';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do {
                usuarioSelected.setId(cursor.getInt(0));
                usuarioSelected.setNombre(cursor.getString(1));
                usuarioSelected.setApellido(cursor.getString(2));
                usuarioSelected.setEmail(cursor.getString(3));
                usuarioSelected.setGenero(cursor.getString(4));
                usuarioSelected.setTelefono(cursor.getString(5));
                usuarioSelected.setContrasena(cursor.getString(6));

            }while (cursor.moveToNext());
        } else {
            usuarioSelected.setNombre("null");
        }
        cursor.close();
        db.close();
        return usuarioSelected;
    };

    //Obtener objeto UsuariosModel por el IdUsuario
    public UsuariosModel getUsuarioModelById(int id){
        UsuariosModel usuarioSelected = new UsuariosModel();

        String queryString = "SELECT * FROM Usuario WHERE IdUsuario = " + id + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do {
                usuarioSelected.setId(cursor.getInt(0));
                usuarioSelected.setNombre(cursor.getString(1));
                usuarioSelected.setApellido(cursor.getString(2));
                usuarioSelected.setEmail(cursor.getString(3));
                usuarioSelected.setGenero(cursor.getString(4));
                usuarioSelected.setTelefono(cursor.getString(5));
                usuarioSelected.setContrasena(cursor.getString(6));

            }while (cursor.moveToNext());
        } else {
            usuarioSelected.setNombre("null");
        }
        cursor.close();
        db.close();
        return usuarioSelected;
    };




}
