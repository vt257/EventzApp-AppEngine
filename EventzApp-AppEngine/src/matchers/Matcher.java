package matchers;

import com.eventzapp.Event;
import com.eventzapp.User;

public abstract class Matcher {
	
	public static final double MAX_WEIGHT = 10;
	
	protected User user;
	protected Event event;
	
	public Matcher(User user, Event event) {
		this.user = user;
		this.event = event;
	}
	
	public User getUser() {
		return user;
	}
	
	public Event getEvent() {
		return event;
	}
	
	public abstract double match();
	
	
	

}
