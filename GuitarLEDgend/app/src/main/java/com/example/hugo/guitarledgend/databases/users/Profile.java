package com.example.hugo.guitarledgend.databases.users;

public class Profile {

    private long id;
    private String nom;
    private String sexe;

    public Profile (long id, String nom, String sexe){
        super();
        this.id=id;
        this.nom=nom;
        this.sexe=sexe;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }





}
