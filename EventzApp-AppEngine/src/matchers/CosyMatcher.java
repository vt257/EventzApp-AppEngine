package matchers;

import com.eventzapp.Event;
import com.eventzapp.User;

public class CosyMatcher extends Matcher {

	public CosyMatcher(User user, Event event) {
		super(user, event);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double match() {
		int attendingFriendsNumber = event.getAttendingFriendIds(user).size();
		int goingNumber = event.getGoingnumber();
		if (goingNumber == 0) 
			return 0;
		return MAX_WEIGHT * attendingFriendsNumber / goingNumber;
	}

}
