package capture;

import java.io.*;

public class Capture {
	public static Float[] wavcapture(String filename)
	   {
	      try
	      {
	         // Open the wav file specified as the first argument
	         WavFile wavFile = WavFile.openWavFile(new File(filename));

	         // Create a buffer of 100 frames
	         double[] buffer = new double[100];

	         int framesRead;
	         
	         Float[] audio = new Float[(int)wavFile.getNumFrames()];
	         
	         int counter = 0;

	         do
	         {
	            // Read frames into buffer
	            framesRead = wavFile.readFrames(buffer, 100);

	            // Loop through frames and add values to audio table
	            for (int s=0; s<framesRead;s++){
	            	Float f = new Float(buffer[s]);
	            	audio[counter]=f;
	            	counter++;
	            }
	            
	         }
	         while (framesRead != 0);

	         // Close the wavFile
	         wavFile.close();
	         return audio;

	      }
	      catch (Exception e)
	      {
	         System.err.println(e);
	         Float[] f = {};
	         return f;
	      }
	   }
}
