package com.example.hugo.guitarledgend.databases.users;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class UsersSQLiteHelper extends SQLiteOpenHelper {

    private static final int VERSION = 2;
    private static final String NOM = "Utilisateurs.db";

    public static final String PROFILE_KEY = "id";
    public static final String PROFILE_NAME = "nom";
    public static final String PROFILE_SEX = "sexe";

    public static final String PROFILE_TABLE_NAME = "PROFILS";
    public static final String PROFILE_TABLE_CREATE = "CREATE TABLE " + PROFILE_TABLE_NAME + " (" + PROFILE_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PROFILE_NAME + " TEXT, " + PROFILE_SEX + " TEXT);";
    public static final String PROFILE_TABLE_DROP = "DROP TABLE IF EXISTS " + PROFILE_TABLE_NAME + ";";

    public static final String STATS_KEY = "id";
    public static final String STATS_DATE= "date";
    public static final String STATS_FICHIER = "fichier";
    public static final String STATS_SCORE = "score";
    public static final String STATS_PARTITION = "partition";
    public static final String STATS_PROFILE= "profil";
    public static final String FOREIGN_KEY = "fk_profil_id";

    public static final String STATS_TABLE_NAME = "STATS";
    public static final String CONTRAINTE = "CONSTRAINT " + FOREIGN_KEY + " FOREIGN KEY (" + STATS_PROFILE +") REFERENCES " + PROFILE_TABLE_NAME + "(" + PROFILE_KEY + ")";
    public static final String STATS_TABLE_CREATE = "CREATE TABLE " + STATS_TABLE_NAME + " (" + STATS_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + STATS_DATE + " TEXT, " + STATS_FICHIER + " TEXT, " + STATS_SCORE + " INTEGER, " + STATS_PARTITION + " INTEGER, " + STATS_PROFILE + " INTEGER, " + CONTRAINTE  +");";
    public static final String STATS_TABLE_DROP = "DROP TABLE IF EXISTS " + STATS_TABLE_NAME + ";";


    public UsersSQLiteHelper (Context context) {
        super(context, NOM, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PROFILE_TABLE_CREATE);
        db.execSQL(STATS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(PROFILE_TABLE_DROP);
        db.execSQL(STATS_TABLE_DROP);
        onCreate(db);
    }






}
