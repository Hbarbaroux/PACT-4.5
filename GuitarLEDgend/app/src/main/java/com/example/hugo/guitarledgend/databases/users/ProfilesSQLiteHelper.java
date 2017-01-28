package com.example.hugo.guitarledgend.databases.users;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ProfilesSQLiteHelper extends SQLiteOpenHelper {

    public String sqlCreate = "CREATE TABLE Profiles (nom TEXT, prenom TEXT, sexe TEXT)";

    public ProfilesSQLiteHelper (Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS Profiles");
        db.execSQL(sqlCreate);
    }



}