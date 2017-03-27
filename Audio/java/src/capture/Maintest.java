package capture;

import attaque.*;

public class Maintest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Float[] audio = Capture.wavcapture("data/melodie.wav");
		System.out.println(audio.length);
		Float[] sheet = sheet(audio);
	}

}
