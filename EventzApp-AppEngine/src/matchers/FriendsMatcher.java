package matchers;

import com.eventzapp.Event;
import com.eventzapp.User;

public class FriendsMatcher extends Matcher {

	public FriendsMatcher(User user, Event event) {
		super(user, event);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double match() {
		int attendingFriendsNumber = event.getAttendingFriendIds(user).size();
		return Math.max(attendingFriendsNumber, MAX_WEIGHT);
	}
	

}
