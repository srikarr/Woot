import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

@SuppressWarnings("serial")
public class FinishedListeningEvent extends Event {
	    
	    public static final EventType<FinishedListeningEvent> FINISHED_LISTENING = new EventType<FinishedListeningEvent>(ANY, "FINISHED_LISTENING");
	    
	    public FinishedListeningEvent() {
	        this(FINISHED_LISTENING);
	    }
	    
	    public FinishedListeningEvent(EventType<? extends Event> arg0) {
	        super(arg0);
	    }
	    public FinishedListeningEvent(Object arg0, EventTarget arg1, EventType<? extends Event> arg2) {
	        super(arg0, arg1, arg2);
	    }  
	}