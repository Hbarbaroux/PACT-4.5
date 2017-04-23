package com.example.hugo.guitarledgend.audio.comparaison;

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
        int na = tabnotes.gettemps().length;
        int ns = temps.length;
        int nj=0 ; //notes justes
        Float diff=this.minecart()/2;
        Boolean[] evalnotes = new Boolean[ns];
        for (int i=0;i<na;i++){
            Float lb=temps[i]-diff;
            Float lh=temps[i]+diff;
            for (int j=0;j<ns;j++){
                if ((tabnotes.gettemps()[j]<=lh) && (tabnotes.gettemps()[j]>=lb) && (Math.abs(freq[j]-tabnotes.getfreq()[j])<0.7) && (evalnotes[j]==false)){
                    evalnotes[j]=true;
                    nj++;

                }
            }
        }
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
}
