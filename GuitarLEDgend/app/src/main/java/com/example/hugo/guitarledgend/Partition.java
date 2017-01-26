package com.example.hugo.guitarledgend;

/**
 * Created by jesusbm on 26/01/17.
 */

public class Partition {

    private String genre;
    private String auteur;
    private String nom;
    private String id;

    public Partition (String genre, String auteur, String nom, String id){
        this.genre=genre;
        this.auteur=auteur;
        this.nom=nom;
        this.id=id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }










}
