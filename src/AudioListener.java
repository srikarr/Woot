import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javafx.event.Event;
import javafx.scene.Scene;
import org.apache.log4j.Logger;

import javax.sound.sampled.TargetDataLine;

public class AudioListener implements Runnable {
	
	private static Logger logger = Logger.getLogger("AudioListener");
	
	private static final int BUFFER_LENGTH = 4;
	
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

		    byteArrayOutputStream.close();
		    
		    FinishedListeningEvent event = new FinishedListeningEvent();
		    Event.fireEvent(parentScene, event);
		} catch (IOException e) {
		    System.err.println("I/O problems: " + e);
		    System.exit(-1);
		}
	}
}
