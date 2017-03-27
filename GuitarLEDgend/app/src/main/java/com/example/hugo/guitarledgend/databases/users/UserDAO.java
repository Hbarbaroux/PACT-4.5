package com.example.hugo.guitarledgend.databases.users;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import com.example.hugo.guitarledgend.databases.partitions.Partition;
import com.example.hugo.guitarledgend.databases.partitions.PartitionsSQLiteHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by jesusbm on 30/01/17.
 */

public class UserDAO {

    private UsersSQLiteHelper mHandler = null;
    private SQLiteDatabase mDb = null ;
    private String[] allProfileColumns = { UsersSQLiteHelper.PROFILE_KEY, UsersSQLiteHelper.PROFILE_NAME, UsersSQLiteHelper.PROFILE_SEX, UsersSQLiteHelper.PROFILE_AGE };


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
        values.put(UsersSQLiteHelper.PROFILE_AGE,p.getAge());
        mDb.insert(UsersSQLiteHelper.PROFILE_TABLE_NAME, null, values);
    }

    public void ajouter (Stats s) {
        ContentValues values = new ContentValues();
        values.put(UsersSQLiteHelper.STATS_DATE,s.getDate());
        values.put(UsersSQLiteHelper.STATS_FICHIER,s.getFichier());
        values.put(UsersSQLiteHelper.STATS_SCORE,s.getScore());
        values.put(UsersSQLiteHelper.STATS_PARTITION,s.getPartition());
        values.put(UsersSQLiteHelper.STATS_PROFILE,s.getProfil());
        mDb.insert(UsersSQLiteHelper.STATS_TABLE_NAME, null, values);
    }


    public void modifier (long profil_id , String nom, int age){
        ContentValues values=new ContentValues();
        values.put(UsersSQLiteHelper.PROFILE_NAME,nom);
        values.put(UsersSQLiteHelper.PROFILE_AGE,String.valueOf(age));

        String[] args = new String[] {String.valueOf(profil_id)};
        String whereClause = UsersSQLiteHelper.PROFILE_KEY + " = ?";

        mDb.update(UsersSQLiteHelper.PROFILE_TABLE_NAME,values,whereClause,args) ;

    }

    public void modifier (Stats s){

    }

    public void supprimerProfil (long id){
        String[] args = new String[] {String.valueOf(id)};
        String whereClause = UsersSQLiteHelper.PROFILE_KEY + " = ?";
        mDb.delete(UsersSQLiteHelper.PROFILE_TABLE_NAME, whereClause, args);

        mDb.execSQL("VACUUM " + UsersSQLiteHelper.PROFILE_TABLE_NAME);

    }

    /*
    public void updateProfileID () {
        int i = 1;
        List<Long> ids = getAllProfilesIds();
        for (long j : ids) {
            mDb.execSQL("UPDATE " + UsersSQLiteHelper.PROFILE_TABLE_NAME + " SET id = " + String.valueOf(i) + " WHERE id = " + String.valueOf(j));
            i++;
        }
    }
    */
    public void supprimerStats (long profile){
        String[] args = new String[] {String.valueOf(profile)};
        String whereClause = UsersSQLiteHelper.STATS_PROFILE + " = ?";
        mDb.delete(UsersSQLiteHelper.STATS_TABLE_NAME, whereClause, args);

        mDb.execSQL("VACUUM " + UsersSQLiteHelper.STATS_TABLE_NAME);

    }

    public void supprimerStats (long partition, long profile){

        String p1= String.valueOf(profile);
        String p2= String.valueOf(partition);

        String[] args = new String[] {p1,p2};
        String whereClause = UsersSQLiteHelper.STATS_PROFILE+" = ? AND "+UsersSQLiteHelper.STATS_PARTITION+" = ?";
        mDb.delete(UsersSQLiteHelper.STATS_TABLE_NAME, whereClause, args);

        mDb.execSQL("VACUUM " + UsersSQLiteHelper.STATS_TABLE_NAME);

    }

/*
    public Stats selectionnerStats (long id){

    }
*/

    public int nombreProfils () {
        Cursor c = mDb.rawQuery("SELECT COUNT(*) FROM "+ UsersSQLiteHelper.PROFILE_TABLE_NAME, null);
        c.moveToFirst();
        return c.getInt(0);
    }

    public int nombreStats (long profile, long partition){
        String whereClause = UsersSQLiteHelper.STATS_PROFILE+" = " + String.valueOf(profile) + " AND " +UsersSQLiteHelper.STATS_PARTITION+" = " + String.valueOf(partition);
        Cursor c = mDb.rawQuery("SELECT COUNT(*) FROM "+ UsersSQLiteHelper.STATS_TABLE_NAME + " WHERE " + whereClause, null);
        c.moveToFirst();
        return c.getInt(0);
    }

    public Profile selectionnerProfile (long id){
        String[] args = new String[] {String.valueOf(id)};
        String whereClause = UsersSQLiteHelper.PROFILE_KEY + " = ?";
        Cursor c = mDb.query(UsersSQLiteHelper.PROFILE_TABLE_NAME, null, whereClause, args, null, null, null);
        c.moveToFirst();
        if (!c.isAfterLast()) {
            Profile p = cursorToProfile(c);
            return p;
        }
        return null;
    }

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

    public List<Long> getAllProfilesIds() {
        List<Long> ids = new ArrayList<Long>();
        List<Profile> profiles = getAllProfiles();

        for (Profile profile : profiles ) {
            ids.add(profile.getId());
        }

        return ids;
    }

    private Profile cursorToProfile(Cursor cursor) {
        Profile profile = new Profile(0, null, null, 0);
        profile.setId(cursor.getLong(0));
        profile.setNom(cursor.getString(1));
        profile.setSexe(cursor.getString(2));
        profile.setAge(cursor.getInt(3));
        return profile;
    }

    public List<Stats> getAllStats(long profile, long partition){
        List<Stats> stats = new ArrayList<Stats>();

        String p1= String.valueOf(profile);
        String p2= String.valueOf(partition);

        String[] args = new String[] {p1,p2};
        String whereClause = UsersSQLiteHelper.STATS_PROFILE+" = ? AND "+UsersSQLiteHelper.STATS_PARTITION+" = ?";
        String orderBy = UsersSQLiteHelper.STATS_KEY + " DESC";
        Cursor cursor = mDb.query (UsersSQLiteHelper.STATS_TABLE_NAME, null,whereClause,args,null,null,orderBy);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Stats s = cursorToStats(cursor);
            stats.add(s);
            cursor.moveToNext();
        }
        cursor.close();
        return stats;
    }

    private Stats cursorToStats (Cursor cursor){
        Stats stats = new Stats (0,null,null,0,0,0);
        stats.setId(cursor.getLong(0));
        stats.setDate(cursor.getString(1));
        stats.setFichier(cursor.getString(2));
        stats.setScore(cursor.getLong(3));
        stats.setPartition(cursor.getLong(4));
        stats.setProfil(cursor.getLong(5));
        return stats;
    }
}
