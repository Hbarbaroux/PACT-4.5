package capture;

import hauteur.*;
import comparaison.*;

public class Maintest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Float[] audio = Capture.wavcapture("data/melodie.wav");
		System.out.println(audio.length);
		Tabnotes sheet = Note.sheet(audio);
		for (int i=0;i<sheet.gettemps().length;i++){
			System.out.println(sheet.gettemps()[i] + ' ' + sheet.getfreq()[i]);
		}
	}

}
