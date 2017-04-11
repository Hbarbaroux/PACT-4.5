package com.example.hugo.guitarledgend.audio.main;

import com.example.hugo.guitarledgend.audio.capture.*;
import com.example.hugo.guitarledgend.audio.comparaison.*;
import com.example.hugo.guitarledgend.audio.hauteur.*;
import com.example.hugo.guitarledgend.audio.sheets.*;


public class Evaluate {

    public CompTable evaluate (String audiofilename, Tablature tab){
        Float[] audio = Capture.wavcapture(audiofilename);
        Tabnotes audiosheet = Note.sheet(audio);
        Tabnotes sheet = tab.translate();
        CompTable comp = audiosheet.compare(sheet);
        return comp;
    }

}