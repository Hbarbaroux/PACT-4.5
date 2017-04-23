package com.example.hugo.guitarledgend.audio.sheets;

import com.example.hugo.guitarledgend.audio.comparaison.Tabnotes;

public class Tablature {

    private int[] corde;

    private int[] doigt;

    private int[] frette;

    private Float[] temps;

    public Tablature (int[] corde, int[] doigt, int[] frette, Float[] temps) {
        this.corde=corde;
        this.doigt=doigt;
        this.frette=frette;
        this.temps=temps;
    }

    public int[] getcorde(){
        return corde;
    }

    public int[] getdoigt(){
        return doigt;
    }

    public int[] getfrette(){
        return frette;
    }

    public Float[] gettemps(){
        return temps;
    }

    public Tabnotes translate(){
        //traduction de la tablature en tableau de notes
        Float[] freq = new Float[corde.length];
        for (int i=0;i<corde.length;i++){
            //la fr�quence de base : le mi grave de la premi�re corde � 82.5 Hz
            int demitons;
            if (corde[i] <= 4){
                demitons = frette[i]-1+(corde[i]-1)*5;}
            else{
                demitons = frette[i]-2+(corde[i]-1)*5;}
            //nb de demi-tons de la note par rapport au mi grave
            freq[i]=(float)(82.5*(Math.pow(2,(float)demitons/12)));
        }
        Tabnotes tabnotes = new Tabnotes (temps,freq);
        return tabnotes;
    }
}
