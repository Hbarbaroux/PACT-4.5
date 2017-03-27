package capture;

import hauteur.*;

public class Maintest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Float[] audio = Capture.wavcapture("data/melodie.wav");
		System.out.println(audio.length);
		Float[] sheet = Note.sheet(audio);
		for (int i=0;i<sheet.length;i++){
			System.out.println(sheet[i]);
		}
	}

}
