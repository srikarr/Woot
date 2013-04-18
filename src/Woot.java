import javafx.geometry.Insets;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class Woot extends Application {
	
	public static boolean running = false;
	public static TargetDataLine targetDataLine;
	public static AudioFormat audioFormat;
	public static AudioListener audioListener;
	
	private static final float SAMPLE_RATE = 44100;
	private static final int SAMPLE_SIZE_IN_BITS = 16;
	private static final int CHANNELS = 2;
	private static final boolean SIGNED = true;
	private static final boolean BIG_ENDIAN = true;
	
	private LineChart<Number,Number> lineChart;
	
	public static void main(String[] args) {
		launch(args);
	}

	private static AudioFormat getFormat() {
	    
	    return new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE_IN_BITS, CHANNELS, SIGNED, BIG_ENDIAN);
	}
	
	private static void SetupAudio() {
		try
		{
			audioFormat = getFormat();
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
			targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
			targetDataLine.open(audioFormat);
			targetDataLine.start();
		} catch (LineUnavailableException e) {
			System.out.println(e.toString());
		}
	}
	
	private void DisplaySpectrogramData(PrincetonComplex[][] results) {
		
	}
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Woot");
		
		StackPane root = new StackPane();
		final Scene theScene = new Scene(root, 300, 250, Color.ALICEBLUE);
		
		theScene.addEventHandler(FinishedListeningEvent.FINISHED_LISTENING, new EventHandler<FinishedListeningEvent>(){

			@Override
			public void handle(FinishedListeningEvent event) {
				if (event.getEventType().equals(FinishedListeningEvent.FINISHED_LISTENING)) {
					Platform.runLater(new Runnable() {
						@Override public void run() {
							final XYChart.Series<Number, Number> series = new Series<Number, Number>();
							series.setName("TargetDataLine");
							
							if (audioListener.audioData != null) {
								for(int i= 0 ; i < 10000; i++) {
									series.getData().add(new Data<Number, Number>(i, audioListener.audioData[i]));
								}
								System.out.println(audioListener.audioData.length);
							}
							
							lineChart.getData().clear();
							lineChart.getData().add(series);
							
							// display the spectrogram data
							DisplaySpectrogramData(audioListener.spectrogramData);
							
							audioListener = null;
						}
					});
				}
			}
			
		});
		
        Button btn = new Button();
        btn.setText("Toggle Listening");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	if (audioListener == null) {
            		audioListener = new AudioListener();
            		audioListener.running = true;
            		audioListener.targetDataLine = targetDataLine;
            		audioListener.parentScene = theScene;
            		
            		Thread worker = new Thread(audioListener);
	    			worker.start();
	    			
	    			System.out.println("Listening!");
            	}
            	else {
            		audioListener.running = false;
            		System.out.println("Not Listening!");
            	}
            }
        });
        
        // build layout
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10, 20, 10, 20));
        
        // build a line chart
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("time");
        lineChart = new LineChart<Number,Number>(xAxis,yAxis);
        
        lineChart.setTitle("audio data");
        
        // something to fill the bottom
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");
        
        
        
        borderPane.setCenter(lineChart);
        borderPane.setTop(btn);
        borderPane.setBottom(hbox);
        
        root.getChildren().add(borderPane);
        
        primaryStage.setScene(theScene);
        primaryStage.show();
        
        System.out.println("Woot!");
		
		SetupAudio();
	}
}
