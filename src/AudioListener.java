import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javafx.event.Event;
import javafx.scene.Scene;
import org.apache.log4j.Logger;

import javax.sound.sampled.TargetDataLine;

public class AudioListener implements Runnable {
	
	private static Logger logger = Logger.getLogger("AudioListener");
	
	private static final int BUFFER_LENGTH = 4;
	private static final int CHUNK_SIZE = 4026;
	
	public boolean running;
	public TargetDataLine targetDataLine;
	public Integer[] audioData;
	public Scene parentScene;
	
	@Override
	public void run() {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		try {
		    while (running) {
		        byte[] buffer = new byte[BUFFER_LENGTH];
		        int count = targetDataLine.read(buffer, 0, buffer.length);
		        if (count > 0) {
		            byteArrayOutputStream.write(buffer, 0, count);
		        }
		    }
		    
		    byte[] byteArray = byteArrayOutputStream.toByteArray();
		    
		    // debugging output to stdio
		    System.out.println(byteArray.length);
		    
		    // save data for use later
		    audioData = new Integer[byteArray.length];
		    for(int i= 0 ; i < byteArray.length; i++) {
		    	audioData[i] = (int) byteArray[i];
		    	logger.info(audioData[i]);
		    }
		    
		    // run FFT for now to test, move out of this class later
		    FFT(byteArrayOutputStream);

		    byteArrayOutputStream.close();
		    
		    FinishedListeningEvent event = new FinishedListeningEvent();
		    Event.fireEvent(parentScene, event);
		} catch (IOException e) {
		    System.err.println("I/O problems: " + e);
		    System.exit(-1);
		}
	}
	
	private void FFT(ByteArrayOutputStream byteArrayOutputStream) {

		byte audio[] = byteArrayOutputStream.toByteArray();
		
		final int totalSize = audio.length;
		
		int amountPossible = totalSize/CHUNK_SIZE;
		
		//When turning into frequency domain we'll need complex numbers:
		Complex[][] results = new Complex[amountPossible][];
		
		// JHB: need doubles as well
		double[][] r = new double[amountPossible][];
		
		//For all the chunks:
		for(int times = 0;times < amountPossible; times++) {
		    Complex[] complex = new Complex[CHUNK_SIZE];
		    
		    // JHB: need 2 array of doubles, real[], imag[] for FFT algorithm
		    double[] real = new double[CHUNK_SIZE];
		    double[] imag = new double[CHUNK_SIZE];
		    
		    for(int i = 0;i < CHUNK_SIZE;i++) {
		        //Put the time domain data into a complex number with imaginary part as 0:
		        complex[i] = new Complex(audio[(times*CHUNK_SIZE)+i], 0);
		        real[i] = audio[(times*CHUNK_SIZE)+i];
		        imag[i] = 0;
		    }
		    
		    //Perform FFT analysis on the chunk:
		    //results[times] = FFT.fft(real, imag, true);
		    r[times] = FFT.fft(real, imag, true);
		}
		
		//Done!

	}
}
