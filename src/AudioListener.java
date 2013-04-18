import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javafx.event.Event;
import javafx.scene.Scene;
import org.apache.log4j.Logger;

import javax.sound.sampled.TargetDataLine;

public class AudioListener implements Runnable {
	
	private static Logger logger = Logger.getLogger("AudioListener");
	
	private static final int BUFFER_LENGTH = 4;
	private static final int CHUNK_SIZE = 4096; // 4096 bytes of data, magic number from http://www.redcode.nl/blog/2010/06/creating-shazam-in-java/ #Discrete Fourier Transform
	
	public boolean running;
	public TargetDataLine targetDataLine;
	public Integer[] audioData;
	public PrincetonComplex[][] spectrogramData;
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
		    spectrogramData = FFT(byteArrayOutputStream);

		    byteArrayOutputStream.close();
		    
		    FinishedListeningEvent event = new FinishedListeningEvent();
		    Event.fireEvent(parentScene, event);
		} catch (IOException e) {
		    System.err.println("I/O problems: " + e);
		    System.exit(-1);
		}
	}
	
	private PrincetonComplex[][] FFT(ByteArrayOutputStream byteArrayOutputStream) {

		byte audio[] = byteArrayOutputStream.toByteArray();
		
		final int totalSize = audio.length;
		
		int amountPossible = totalSize/CHUNK_SIZE;
		
		//When turning into frequency domain we'll need complex numbers:
		PrincetonComplex[][] results = new PrincetonComplex[amountPossible][];
		
		// JHB: need doubles as well
		//double[][] r = new double[amountPossible][];
		
		//For all the chunks:
		for(int times = 0;times < amountPossible; times++) {
			PrincetonComplex[] complex = new PrincetonComplex[CHUNK_SIZE];
		    
		    // JHB: need 2 array of doubles, real[], imag[] for FFT algorithm
		    //double[] real = new double[CHUNK_SIZE];
		    //double[] imag = new double[CHUNK_SIZE];
		    
		    for(int i = 0;i < CHUNK_SIZE;i++) {
		        //Put the time domain data into a complex number with imaginary part as 0:
		        complex[i] = new PrincetonComplex(audio[(times*CHUNK_SIZE)+i], 0);
		        //real[i] = audio[(times*CHUNK_SIZE)+i];
		        //imag[i] = 0;
		    }
		    
		    //Perform FFT analysis on the chunk:
		    results[times] = PrincetonFFT.fft(complex);
		    //r[times] = OrlandoSelenuFFT.fft(real, imag, true);
		}
		
		return results;
		//Done!

	}
}
