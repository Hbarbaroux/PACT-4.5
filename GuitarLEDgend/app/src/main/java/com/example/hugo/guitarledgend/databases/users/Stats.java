package com.example.hugo.guitarledgend.databases.users;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Stats {

    private long id;
    private String date;
    private String fichier;
    private long score;
    private long partition;
    private long profil;

    public Stats (long id, String date, String fichier, long score, long partition, long profil){
        super();
        this.id=id;
        this.date=date;
        this.fichier=fichier;
        this.score=score;
        this.partition=partition;
        this.profil=profil;
    }

    public long getProfil() {
        return profil;
    }

    public void setProfil(long profil) {
        this.profil = profil;
    }

    public String getFichier() {
        return fichier;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public long getPartition() {
        return partition;
    }

    public void setPartition(long partition) {
        this.partition = partition;
    }

    public List<Integer> tabFromFile (Context context){
        List<Integer> tab = new ArrayList<>();
        BufferedReader bf = null;
        /*
        InputStream is;
        AssetManager assetManager= context.getAssets();
        try {
            is = assetManager.open("data/" + fichier);

            bf = new BufferedReader(new InputStreamReader(is));

            String line = bf.readLine();
            while (line != null){
                if (line.equals("0")) {
                    tab.add(0);
                }
                if (line.equals("1")) {
                    tab.add(1);
                }
                line = bf.readLine();
            }


        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if(bf!=null){
                try{
                    bf.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        */
        File phone = context.getFilesDir();
        File file = new File(phone,"/statsData/" + fichier);
        if (file.exists()){
            try {
                bf = new BufferedReader(new FileReader(file));
                String line = bf.readLine();
                while (line != null){
                    if (line.equals("0")) {
                        tab.add(0);
                    }
                    if (line.equals("1")) {
                        tab.add(1);
                    }
                    line = bf.readLine();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                if(bf!=null){
                    try{
                        bf.close();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }

        return tab;
    }




}
