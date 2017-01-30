package com.example.hugo.guitarledgend.databases.users;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by jesusbm on 30/01/17.
 */

public class UserDAO {

    private UsersSQLiteHelper mHandler = null;
    private SQLiteDatabase mDb = null ;
    private String[] allProfileColumns = { UsersSQLiteHelper.PROFILE_KEY, UsersSQLiteHelper.PROFILE_NAME, UsersSQLiteHelper.PROFILE_SEX };


    public UserDAO(Context Context) {
        mHandler = new UsersSQLiteHelper(Context);
    }

    public void open() throws SQLException {
        mDb = mHandler.getWritableDatabase();
    }

    public void close() {
        mDb.close();
    }

    public SQLiteDatabase getDb() {
        return mDb;
    }



    public void ajouter (Profile p) {
        ContentValues values = new ContentValues();
        values.put(UsersSQLiteHelper.PROFILE_NAME,p.getNom());
        values.put(UsersSQLiteHelper.PROFILE_SEX,p.getSexe());
        mDb.insert(UsersSQLiteHelper.PROFILE_TABLE_NAME, null, values);
    }

    public void ajouter (Stats s) {
        ContentValues values = new ContentValues();
        values.put(UsersSQLiteHelper.STATS_DATE,s.getDate());
        values.put(UsersSQLiteHelper.STATS_FICHIER,s.getFichier());
        values.put(UsersSQLiteHelper.STATS_PROFILE,s.getProfil());
        mDb.insert(UsersSQLiteHelper.STATS_TABLE_NAME, null, values);
    }


    public void modifier (Profile p){

    }

    public void modifier (Stats s){

    }

    public void supprimerProfil (long id){

    }

    public void supprimerStats (long id){

    }

    /*
    public Stats selectionnerStats (long id){

    }

    public Profile selectionnerProfile (long id){

    }
    */


    public List<Profile> getAllProfiles() {
        List<Profile> profiles = new ArrayList<Profile>();

        Cursor cursor = mDb.query(UsersSQLiteHelper.PROFILE_TABLE_NAME, allProfileColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Profile profile = cursorToProfile(cursor);
            profiles.add(profile);
            cursor.moveToNext();
        }
        cursor.close();
        return profiles;
    }

    private Profile cursorToProfile(Cursor cursor) {
        Profile profile = new Profile(0, null, null);
        profile.setId(cursor.getLong(0));
        profile.setNom(cursor.getString(1));
        profile.setSexe(cursor.getString(2));
        return profile;
    }
}
