package com.example.hugo.guitarledgend;

public class Partition {

    private long id;
    private String fichier;
    private String nom;
    private String auteur;
    private String genre;



    public Partition (long id, String fichier, String genre, String auteur, String nom){
        super();
        this.id=id;
        this.fichier=fichier;
        this.genre=genre;
        this.auteur=auteur;
        this.nom=nom;
    }


    public String getFichier() {
        return fichier;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }










}
