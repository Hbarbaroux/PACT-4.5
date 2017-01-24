package com.example.hugo.guitarledgend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;


public class StatsSQLiteHelper extends SQLiteOpenHelper {

    String sqlCreate = "CREATE TABLE Stats (annee TEXT, mois TEXT, jour TEXT, score INTEGER , id TEXT)";

    public StatsSQLiteHelper (Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS Stats");
        db.execSQL(sqlCreate);
    }



}