import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class Woot extends Application {
	
	public static boolean running = false;
	public static TargetDataLine line;
	public static AudioFormat format;
	public static Listen listenTask;
	
	public static void main(String[] args) {
		launch(args);
	}

	private static AudioFormat getFormat() {
	    float sampleRate = 44100;
	    int sampleSizeInBits = 16;
	    int channels = 2;
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
	
	@Override
	public void start (Stage primaryStage) {
		primaryStage.setTitle("Woot");
        Button btn = new Button();
        btn.setText("Toggle Listening");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	if (listenTask == null) {
            		listenTask = new Listen();
            		listenTask.running = true;
            		listenTask.line = line;
            		
            		Thread worker = new Thread(listenTask);
	    			worker.start();
	    			
	    			System.out.println("Listening!");
            	}
            	else {
            		listenTask.running = false;
            		listenTask = null;
            		System.out.println("Not Listening!");
            	}
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
        
        System.out.println("Woot!");
		
		SetupAudio();
	}
}
