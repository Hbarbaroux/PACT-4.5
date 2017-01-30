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

/**
 * Created by jesusbm on 30/01/17.
 */

public class UserDAO {

    private UsersSQLiteHelper mHandler = null;
    private SQLiteDatabase mDb = null ;
    private String[] allColumns = { com.example.hugo.guitarledgend.databases.partitions.PartitionsSQLiteHelper.PARTITION_KEY, com.example.hugo.guitarledgend.databases.partitions.PartitionsSQLiteHelper.PARTITION_FILE, com.example.hugo.guitarledgend.databases.partitions.PartitionsSQLiteHelper.PARTITION_NAME, com.example.hugo.guitarledgend.databases.partitions.PartitionsSQLiteHelper.PARTITION_AUTHOR, com.example.hugo.guitarledgend.databases.partitions.PartitionsSQLiteHelper.PARTITION_GENRE};


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

        mDb.insert(com.example.hugo.guitarledgend.databases.partitions.PartitionsSQLiteHelper.PARTITION_TABLE_NAME, null, values);
    }

    public void modifier (Partition p){

    }

    public void supprimer (String id){

    }

    /*    public long selectionner (String genre, String auteur, String nom){
            String[] args = new String[] {genre,auteur,nom};
            Cursor c = mDb.rawQuery("SELECT "+KEY+" FROM "+TABLE_NAME+" WHERE "+GENRE+" = ? , "+AUTHOR+" = ? , "+NOM+" = ?", args);
            c.moveToFirst();
            long id = c.getLong(0);
            return id;
        }
    */
    public List<Partition> getAllProfiles() {
        List<Partition> partitions = new ArrayList<Partition>();

        Cursor cursor = mDb.query(com.example.hugo.guitarledgend.databases.partitions.PartitionsSQLiteHelper.PARTITION_TABLE_NAME, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Partition partition = cursorToPartition(cursor);
            partitions.add(partition);
            cursor.moveToNext();
        }
        cursor.close();
        return partitions;
    }

    private Partition cursorToPartition(Cursor cursor) {
        Partition partition = new Partition(0, null, null, null, null);
        partition.setId(cursor.getLong(0));
        partition.setFichier(cursor.getString(1));
        partition.setNom(cursor.getString(2));
        partition.setAuteur(cursor.getString(3));
        partition.setGenre(cursor.getString(4));
        return partition;
    }
}
