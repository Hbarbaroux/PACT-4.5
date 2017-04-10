package main;

import capture.*;
import comparaison.*;
import hauteur.*;
import sheets.*;


public class Evaluate {
	
	public CompTable evaluate (String audiofilename, Tablature tab){
		Float[] audio = Capture.wavcapture(audiofilename);
		Tabnotes audiosheet = Note.sheet(audio);
		Tabnotes sheet = tab.translate();
		CompTable comp = audiosheet.compare(sheet);
		return comp;
	}

}
