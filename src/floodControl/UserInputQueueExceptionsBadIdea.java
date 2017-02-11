package floodControl;

import java.util.ArrayDeque;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class UserInputQueueExceptionsBadIdea {

	private ArrayDeque<KeyEvent> keyEventsQueue;
	private ArrayDeque<MouseEvent> mouseEventsQueue;
	
	public UserInputQueueExceptionsBadIdea(){
		
		keyEventsQueue = new ArrayDeque<KeyEvent>();
		mouseEventsQueue = new ArrayDeque<MouseEvent>();
	}
	
	public void addKey(KeyEvent keyEv){
		keyEventsQueue.add(keyEv);		
	}
	
	public void addMouse(MouseEvent mouseEv){
		mouseEventsQueue.add(mouseEv);		
	}
	
	public KeyEvent getKey() throws NullPointerException{
		if(keyEventsQueue.isEmpty()){
			throw new NullPointerException();
			//return null;
		}
		else{
			KeyEvent evLast = keyEventsQueue.getFirst();
			keyEventsQueue.removeFirst();
			
			return evLast;
		}
	}
	
	public MouseEvent getMouse() throws NullPointerException{
		if(keyEventsQueue.isEmpty()){
			throw new NullPointerException();
			//return null;
		}
		else{
			MouseEvent evLast = mouseEventsQueue.getFirst();
			mouseEventsQueue.removeFirst();
			
			return evLast;
		}
	}
	
}
