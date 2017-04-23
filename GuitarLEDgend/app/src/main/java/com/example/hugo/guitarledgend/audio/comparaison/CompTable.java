package com.example.hugo.guitarledgend.audio.comparaison;

import android.graphics.Interpolator;
import android.util.Log;

import com.example.hugo.guitarledgend.audio.capture.Capture;
import com.example.hugo.guitarledgend.audio.hauteur.Note;
import com.example.hugo.guitarledgend.audio.sheets.Tablature;

import java.util.Arrays;

public class CompTable {

    private boolean[] evalnotes;

    private int entrop;

    public CompTable (boolean[] evalnotes, int entrop){
        this.evalnotes=evalnotes;
        this.entrop=entrop;
    }

    public boolean[] getevalnotes(){
        return evalnotes;
    }

    public int getentrop(){
        return entrop;
    }

    public CompTable evaluate (String audiofilename, Tablature tab){
        Float[] audio = Capture.wavcapture(audiofilename);
        audio = enleve(audio);
        Log.e("TAG", Integer.toString(audio.length));
        Tabnotes audiosheet = Note.sheet(audio);
        Tabnotes sheet = tab.translate();
        CompTable comp = sheet.compare(audiosheet);
        return comp;
    }

    private static Float[] enleve(Float[] audio) {
        int i = 0;
        while (audio[i]==0) {
            i++;
        }

        return (Arrays.copyOfRange(audio, i, audio.length));
    }
}