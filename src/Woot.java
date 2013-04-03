
import java.io.Console;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class Woot {
	
	public static boolean running = true;
	public static TargetDataLine line;
	public static AudioFormat format;
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		System.out.println("Woot!");
		
		SetupAudio();
		
		Listen listenTask = new Listen();
		listenTask.running = running;
		listenTask.line = line;
		
		Console console = System.console();
        if (console != null)
        {
			Thread worker = new Thread(listenTask);
			worker.start();
		
			//blocking
	        @SuppressWarnings("unused")
			String readLine = console.readLine("Press any key to stop listening: ", (Object[])null);
	        worker.stop();
	        
	        System.out.println("All done.");
        }
	}

	private static AudioFormat getFormat() {
	    float sampleRate = 44100;
	    int sampleSizeInBits = 8;
	    int channels = 1;
	    boolean signed = true;
	    boolean bigEndian = true;
	    return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}
	
	private static void SetupAudio() {
		try
		{
			format = getFormat();
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
			line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(format);
			line.start();
		} catch (LineUnavailableException e) {
			System.out.println(e.toString());
		}
	}
	
}
