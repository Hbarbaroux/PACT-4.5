package com.example.hugo.guitarledgend.audio.comparaison;

import android.util.Log;

public class Tabnotes {

    private final Float[] temps;

    private final Float[] freq;

    public Tabnotes (Float[] temps, Float[] freq){
        this.temps=temps;
        this.freq=freq;
    }

    public Float[] gettemps(){
        return temps;
    }

    public Float[] getfreq(){
        return freq;
    }

    public CompTable compare(Tabnotes tabnotes){
        print("audiot",tabnotes.gettemps());
        print("audiof",tabnotes.getfreq());
        print("realt",this.gettemps());
        print("realf",this.getfreq());
        int na = tabnotes.gettemps().length;
        int ns = temps.length;
        int nj=0 ; //notes justes
        Float diff=this.minecart();
        boolean[] evalnotes = new boolean[ns];
        print("bol",evalnotes);
        for (int i=0;i<ns;i++){
            Float lb=temps[i];
            Float lh=temps[i]+diff;
            for (int j=0;j<na;j++){
                if ((tabnotes.gettemps()[j]<=lh) && (tabnotes.gettemps()[j]>=lb) && (Math.abs(freq[i]-tabnotes.getfreq()[j])<0.7) && (evalnotes[i]==false)){
                    evalnotes[i]=true;
                    nj++;

                }
            }
        }
        print("bol2",evalnotes);
        int entrop = ns-nj;
        CompTable comptable = new CompTable(evalnotes, entrop);
        return comptable;
    }

    public Float minecart (){
        //calcule l'�cart minimal entre deux notes du tableau Tabnotes
        Float min = temps[temps.length-1];
        for (int i=1;i<temps.length;i++){
            Float e = temps[i]-temps[i-1];
            if (e<min){
                min=e;
            }

        }
        return min;
    }

    public static void print(String tag, boolean[] bol){
        String rep = "";
        for (int i=0;i<bol.length;i++){
            if (i==0) {
                rep=rep+"["+bol[i];
            }
            else if (i==bol.length-1){
                rep=rep+";"+bol[i]+"]";
            }
            else {
                rep=rep+";"+bol[i];
            }
        }
        Log.e(tag,rep);
    }

    public static void print(String tag, Float[] bol){
        String rep = "";
        for (int i=0;i<bol.length;i++){
            if (i==0) {
                rep=rep+"["+bol[i];
            }
            else if (i==bol.length-1){
                rep=rep+";"+bol[i]+"]";
            }
            else {
                rep=rep+";"+bol[i];
            }
        }
        Log.e(tag,rep);
    }
}
