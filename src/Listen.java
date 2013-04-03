import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.sound.sampled.TargetDataLine;


public class Listen implements Runnable {
	
	public boolean running;
	public TargetDataLine line;
	
	@Override
	public void run() {
		OutputStream out = new ByteArrayOutputStream();

		try {
		    while (running) {
		        byte[] buffer = new byte[1024];
		        int count = line.read(buffer, 0, buffer.length);
		        if (count > 0) {
		            out.write(buffer, 0, count);
		        }
		    }
		    out.close();
		} catch (IOException e) {
		    System.err.println("I/O problems: " + e);
		    System.exit(-1);
		}
	}

}
