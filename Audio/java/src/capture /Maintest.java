package capture;

import com.example.hugo.guitarledgend.audio.attaque.Attaque;
import com.example.hugo.guitarledgend.audio.comparaison.Tabnotes;

import attaque.*;
import hauteur.*;
import comparaison.*;

public class Maintest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Float[] A = {(float)2,(float)4};
		Float[] B = {(float)2,(float)6};
		Float[] X = {(float)2,(float)7,(float)4,(float)7,(float)6};
		Attaque.print(Attaque.filter(A,B,X));
		Float[] audio = Capture.wavcapture("data/melodie.wav");
		System.out.println(audio.length);
		Tabnotes sheet = Note.sheet(audio);
		System.out.println(sheet.gettemps().length);
		for (int i=0;i<sheet.gettemps().length;i++){
			System.out.print(sheet.gettemps()[i] + "   ");
			System.out.println(sheet.getfreq()[i]);
		}
	}

}
