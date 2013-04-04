import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.sound.sampled.TargetDataLine;


public class Listen implements Runnable {
	
	public boolean running;
	public TargetDataLine line;
	
	@Override
	public void run() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
		    while (running) {
		        byte[] buffer = new byte[4];
		        int count = line.read(buffer, 0, buffer.length);
		        if (count > 0) {
		            out.write(buffer, 0, count);
		        }
		    }
		    
		    // debugging output
		    byte[] byteArray = out.toByteArray();
		    System.out.println(byteArray.length);
		    for(int i= 0 ; i < byteArray.length; i++) {
		    	System.out.println(byteArray[i]);
		    }

		    out.close();
		} catch (IOException e) {
		    System.err.println("I/O problems: " + e);
		    System.exit(-1);
		}
	}

}
