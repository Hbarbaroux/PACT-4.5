package com.example.hugo.guitarledgend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class PartitionsSQLiteHelper extends SQLiteOpenHelper {

    public static final String PARTITION_KEY = "id";
    public static final String PARTITION_FILE = "fichier";
    public static final String PARTITION_NAME = "nom";
    public static final String PARTITION_AUTHOR = "auteur";
    public static final String PARTITION_GENRE = "genre";


    private static final int VERSION = 1;
    private static final String NOM = "Partitions.db";
    public static final String PARTITION_TABLE_NAME = "PARTITIONS";
    public static final String PARTITION_TABLE_CREATE = "CREATE TABLE " + PARTITION_TABLE_NAME + " (" + PARTITION_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PARTITION_FILE + " TEXT, " + PARTITION_NAME + " TEXT, " + PARTITION_AUTHOR + " TEXT, " + PARTITION_GENRE + " TEXT);";
    public static final String PARTITION_TABLE_DROP = "DROP TABLE IF EXISTS " + PARTITION_TABLE_NAME + ";";



    public PartitionsSQLiteHelper (Context context) {
        super(context, NOM, null, VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PARTITION_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(PARTITION_TABLE_DROP);
        onCreate(db);
    }
}