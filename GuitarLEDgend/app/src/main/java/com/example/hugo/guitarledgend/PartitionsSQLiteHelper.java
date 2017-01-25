package com.example.hugo.guitarledgend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;


public class PartitionsSQLiteHelper extends SQLiteOpenHelper {

    public String sqlCreate = "CREATE TABLE Music (genre TEXT, auteur TEXT, nom TEXT, id TEXT)";

    public PartitionsSQLiteHelper (Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS Music");
        db.execSQL(sqlCreate);
    }



}