import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.sound.sampled.TargetDataLine;


public class Listener implements Runnable {
	
	private static final int BUFFER_LENGTH = 4;
	private static final String DEBUG_FILE = "debugging.log";
	
	public boolean running;
	public TargetDataLine targetDataLine;
	public Integer[] audioData;
	
	@Override
	public void run() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
		    while (running) {
		        byte[] buffer = new byte[BUFFER_LENGTH];
		        int count = targetDataLine.read(buffer, 0, buffer.length);
		        if (count > 0) {
		            out.write(buffer, 0, count);
		        }
		    }
		    
		    // debugging output to stdio
		    byte[] byteArray = out.toByteArray();
		    
		    System.out.println(byteArray.length);
		    
		    for(int i= 0 ; i < byteArray.length; i++) {
		    	System.out.println(byteArray[i]);
		    }
		    
		    // save data for use later
		    audioData = new Integer[byteArray.length];
		    for(int i= 0 ; i < byteArray.length; i++) {
		    	audioData[i] = (int) byteArray[i];
		    }
		    
		    // debugging output to file
		    FileWriter output = null;
		    PrintWriter writer = null;
		    try {
		    	output = new FileWriter(DEBUG_FILE);
		    	writer = new PrintWriter(output);
		    	for(int i= 0 ; i < byteArray.length; i++) {
		    		writer.println(byteArray[i]);
			    }
		    } catch (Exception e) {
		    	System.err.println("Debugging file I/O problems: " + e);
			    System.exit(-1);
		    } finally {
		    	if (output != null) {
		            try {
		              output.close();
		            } catch (IOException e) {
		              // Ignore issues during closing
		            }
		    	}
		    	if (writer != null) {
		            try {
		            	writer.close();
		            } catch (Exception e) {
		              // Ignore issues during closing
		            }
		    	}
		    }

		    out.close();
		} catch (IOException e) {
		    System.err.println("I/O problems: " + e);
		    System.exit(-1);
		}
	}

}
