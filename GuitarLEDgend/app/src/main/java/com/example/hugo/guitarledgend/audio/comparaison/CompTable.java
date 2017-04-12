package com.example.hugo.guitarledgend.audio.comparaison;

import com.example.hugo.guitarledgend.audio.capture.Capture;
import com.example.hugo.guitarledgend.audio.hauteur.Note;
import com.example.hugo.guitarledgend.audio.sheets.Tablature;

public class CompTable {

    private Boolean[] evalnotes;

    private int entrop;

    public CompTable (Boolean[] evalnotes, int entrop){
        this.evalnotes=evalnotes;
        this.entrop=entrop;
    }

    public Boolean[] getevalnotes(){
        return evalnotes;
    }

    public int getentrop(){
        return entrop;
    }

    public CompTable evaluate (String audiofilename, Tablature tab){
        Float[] audio = Capture.wavcapture(audiofilename);
        Tabnotes audiosheet = Note.sheet(audio);
        Tabnotes sheet = tab.translate();
        CompTable comp = audiosheet.compare(sheet);
        return comp;
    }
}
