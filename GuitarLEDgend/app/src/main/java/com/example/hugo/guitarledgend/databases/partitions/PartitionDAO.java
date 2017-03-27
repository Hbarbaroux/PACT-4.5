package com.example.hugo.guitarledgend.databases.partitions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.hugo.guitarledgend.databases.users.Profile;
import com.example.hugo.guitarledgend.databases.users.UsersSQLiteHelper;

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

    public void modifier (long id, String nom, String auteur, String genre){
        ContentValues values=new ContentValues();
        values.put(PartitionsSQLiteHelper.PARTITION_NAME,nom);
        values.put(PartitionsSQLiteHelper.PARTITION_AUTHOR,auteur);
        values.put(PartitionsSQLiteHelper.PARTITION_GENRE,genre);

        String[] args = new String[] {String.valueOf(id)};
        String whereClause = PartitionsSQLiteHelper.PARTITION_KEY + " = ?";

        mDb.update(PartitionsSQLiteHelper.PARTITION_TABLE_NAME,values,whereClause,args) ;

    }

    public void supprimer (long id){
        mDb.execSQL("DELETE FROM " + PartitionsSQLiteHelper.PARTITION_TABLE_NAME + " WHERE id = " + String.valueOf(id));
        mDb.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + PartitionsSQLiteHelper.PARTITION_TABLE_NAME + "'");
        mDb.execSQL("VACUUM " + PartitionsSQLiteHelper.PARTITION_TABLE_NAME);
        updatePartitionID();
    }

    public void updatePartitionID () {
        int i = 1;
        List<Long> ids = getAllPartitionsIds();
        for (long j : ids) {
            mDb.execSQL("UPDATE " + PartitionsSQLiteHelper.PARTITION_TABLE_NAME + " SET id = " + String.valueOf(i) + " WHERE id = " + String.valueOf(j));
            i++;
        }
    }

    public List<Long> getAllPartitionsIds() {
        List<Long> ids = new ArrayList<Long>();
        List<Partition> partitions = getAllPartitions();

        for (Partition partition : partitions ) {
            ids.add(partition.getId());
        }
        return ids;
    }

    public Partition selectionner (long id){
        String[] args = new String[] {String.valueOf(id)};
        String whereClause = PartitionsSQLiteHelper.PARTITION_KEY + " = ?";
        Cursor c = mDb.query(PartitionsSQLiteHelper.PARTITION_TABLE_NAME, null, whereClause, args, null, null, null);
        c.moveToFirst();
        Partition p = cursorToPartition(c);
        return p;
    }

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
