package com.example.hugo.guitarledgend.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class PartitionDAO {

    private PartitionsSQLiteHelper mHandler = null;
    private SQLiteDatabase mDb = null ;
    private String[] allColumns = { PartitionsSQLiteHelper.PARTITION_KEY, PartitionsSQLiteHelper.PARTITION_FILE, PartitionsSQLiteHelper.PARTITION_NAME, PartitionsSQLiteHelper.PARTITION_AUTHOR, PartitionsSQLiteHelper.PARTITION_GENRE};


    public PartitionDAO(Context Context) {
        mHandler = new PartitionsSQLiteHelper(Context);
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


    public void ajouter (Partition p) {
        ContentValues values = new ContentValues();
        values.put(PartitionsSQLiteHelper.PARTITION_FILE,p.getFichier());
        values.put(PartitionsSQLiteHelper.PARTITION_NAME,p.getNom());
        values.put(PartitionsSQLiteHelper.PARTITION_AUTHOR,p.getAuteur());
        values.put(PartitionsSQLiteHelper.PARTITION_GENRE, p.getGenre());

        mDb.insert(PartitionsSQLiteHelper.PARTITION_TABLE_NAME, null, values);
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
    public List<Partition> getAllPartitions() {
        List<Partition> partitions = new ArrayList<Partition>();

        Cursor cursor = mDb.query(PartitionsSQLiteHelper.PARTITION_TABLE_NAME, allColumns, null, null, null, null, null);

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
