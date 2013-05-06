package matchers;

import com.eventzapp.Event;
import com.eventzapp.Gender;
import com.eventzapp.User;

public class FlirtyMatcher extends Matcher {
	
	private Gender gender = null;

	public FlirtyMatcher(User user, Event event, Gender gender) {
		super(user, event);
		
		this.gender = gender;
	}

	@Override
	public double match() {
		int singlesOppositeGenderNumber = event.getAttending(gender).size();
		int goingNumber = event.getGoingnumber();
		if (goingNumber == 0)
			return 0;
		return MAX_WEIGHT * singlesOppositeGenderNumber / goingNumber;
	}

}
