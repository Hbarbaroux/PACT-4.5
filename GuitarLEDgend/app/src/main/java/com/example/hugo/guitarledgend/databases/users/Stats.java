package com.example.hugo.guitarledgend.databases.users;

public class Stats {

    private long id;
    private String date;
    private String fichier;
    private long profil;

    public Stats (long id, String date, String fichier, long profil){
        super();
        this.id=id;
        this.date=date;
        this.fichier=fichier;
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




}
