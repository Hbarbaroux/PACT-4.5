package com.example.hugo.guitarledgend;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PartitionDAO {

    private int VERSION;
    private PartitionsSQLiteHelper ssqlh = null;
    private SQLiteDatabase db = null ;


    public PartitionDAO(Context pContext) {
        this.ssqlh = new PartitionsSQLiteHelper(pContext, "MyMusic", null, VERSION);
    }

    public SQLiteDatabase open() {
        // Pas besoin de fermer la dernière base puisque getWritableDatabase s'en charge
        db = ssqlh.getWritableDatabase();
        return db;
    }

    public void close() {
        db.close();
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    //propre a cette table

    public static final String TABLE_NAME = "Music";
    public static final String ID = "id";
    public static final String NOM = "nom";
    public static final String AUTEUR = "auteur";
    public static final String GENRE = "genre";


    public void ajouter (Partition p) {
        ContentValues newPartition = new ContentValues();
        newPartition.put(GENRE, p.getGenre());
        newPartition.put(AUTEUR,p.getAuteur());
        newPartition.put(NOM,p.getNom());
        newPartition.put(ID,p.getId());
        //insert the new partition into the database
        db.insert(TABLE_NAME, null, newPartition);
    }

    public void modifier (Partition p){

    }

    public void supprimer (String id){

    }

    public String selectionner (String genre, String auteur, String nom){
        String[] args = new String[] {genre,auteur,nom};
        Cursor c = db.rawQuery("SELECT "+ID+" FROM "+TABLE_NAME+" WHERE "+GENRE+" = ? , "+AUTEUR+" = ? , "+NOM+" = ?", args);
        c.moveToFirst();
        String id = c.getString(4);
        return id;
    }





}
