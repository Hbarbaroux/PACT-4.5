package capture;

public class Maintest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Float[] audio = Capture.wavcapture("data/testla.wav");
		System.out.println(audio.length);
		for (int i=0;i<audio.length; i=i+1000){
			System.out.println(audio[i].floatValue());
		}
	}

}
