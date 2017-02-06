package com.example.hugo.guitarledgend.databases.users;

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

    public List<Boolean> tabFromFile (){
        return null;
    }




}
