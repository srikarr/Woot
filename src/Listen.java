import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
		    
		    // debugging output to stdio
		    byte[] byteArray = out.toByteArray();
		    System.out.println(byteArray.length);
		    for(int i= 0 ; i < byteArray.length; i++) {
		    	System.out.println(byteArray[i]);
		    }
		    
		    // debugging output to file
		    FileWriter output = null;
		    PrintWriter writer = null;
		    try {
		    	output = new FileWriter("debugging.txt");
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
