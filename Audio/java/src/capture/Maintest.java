package capture;

public class Maintest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Float[] audio = Capture.wavcapture("testla.wav");
		System.out.println(audio.length);
		System.out.println(audio);
	}

}
