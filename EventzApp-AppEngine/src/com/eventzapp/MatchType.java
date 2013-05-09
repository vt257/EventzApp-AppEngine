package com.eventzapp;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MatchType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	private String name;
	// the method to use when matching
	private String matchMethod;
	
	public static final String FRIENDS_MATCH_NAME = "Number of Friends";
	public static final String FRIENDS_MATCH_METHOD = "friends";
	public static final String NEWPEOPLE_MATCH_NAME = "Meet new people";
	public static final String NEWPEOPLE_MATCH_METHOD = "newpeople";
	public static final String FLIRTY_MATCH_NAME = "Flirty?";
	public static final String FLIRTY_MATCH_METHOD = "flirty";
	public static final String LOCATION_MATCH_NAME = "Location";
	public static final String LOCATION_MATCH_METHOD = "location";
	public static final String TIME_MATCH_NAME = "Time";
	public static final String TIME_MATCH_METHOD = "time";
	public static final String INTERESTS_MATCH_NAME = "Interests";
	public static final String INTERESTS_MATCH_METHOD = "interests";
	public static final String HISTORY_MATCH_NAME = "History";
	public static final String HISTORY_MATCH_METHOD = "history";
	
	public static final String PARTY_DEFAULT_MATCH_NAME = "Party";
	public static final String WORKEDUCATIONBUSINESS_DEFAULT_MATCH_NAME = "Work/Education/Business";
	public static final String CAUSE_DEFAULT_MATCH_NAME = "Cause";
	public static final String KEYWORDMAP_MATCH_METHOD = "keywordmap";

	public MatchType() {
	}
	/**
	 * 
	 * @param name the name of the matchtype to be displayed to the user
	 * @param matchMethod the matchmethod
	 */
	public MatchType(Long uid, String name, String matchMethod) {
		this.name = name;
		this.matchMethod = matchMethod;
		// TODO this is now done for keeping the same user from creating matchtypes with the same names because for each user
		// the name is the unique identifier. Later on changes this to some good way after the ancestor/child part is clear
		// also change the type to Long
		this.id = uid+"&&"+name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMatchMethod() {
		return matchMethod;
	}
	public void setMatchMethod(String matchMethod) {
		this.matchMethod = matchMethod;
	}
	/**
	 * function to insert the default keywords
	 * the keyword should have an ancestor of a type MatchType
	 */
	public void insertDefaultKeywords() {
		// TODO implement this method
	}
	
	/**
	 * The method to handle the matching given the 
	 * User and the Event
	 * @return
	 */
	public Float match(User user, Event event) {
		Match match = new Match();
		match.setMatchTypeId(this.getId());
		match.setEid(event.getEid());
		switch (this.getMatchMethod()) {
			case FRIENDS_MATCH_METHOD:
				match.setMatch(matchFriends(user, event));
			case NEWPEOPLE_MATCH_METHOD:
				match.setMatch(matchNewPeople(user, event));
			case FLIRTY_MATCH_METHOD:
				match.setMatch(matchFlirty(user, event));
			case LOCATION_MATCH_METHOD:
				match.setMatch(matchLocation(user, event));
			case TIME_MATCH_METHOD:
				match.setMatch(matchTime(user, event));
			case INTERESTS_MATCH_METHOD:
				match.setMatch(matchInterests(user, event));
			case HISTORY_MATCH_METHOD:
				match.setMatch(matchHistory(user, event));
			case KEYWORDMAP_MATCH_METHOD:
			default:
				match.setMatch(matchKeywordMap(user, event));
		}		
		return null;
	}
	
	private Float matchFriends(User user, Event event) {
		// TODO match according to the number of friends attending the event
		return null;
	}

	private Float matchNewPeople(User user, Event event) {
		// TODO match according to the percentage of new people
		return null;
	}

	private Float matchFlirty(User user, Event event) {
		// TODO match according to the percentage of male/female depending on the sex of the user
		return null;
	}
	
	private Float matchLocation(User user, Event event) {
		// TODO match using the locations of the event and the user
		return null;
	}

	private Float matchTime(User user, Event event) {
		// TODO match using the time of the event by comparing with the local android calendar of the user
		return null;
	}

	private Float matchInterests(User user, Event event) {
		// TODO match using the interests of the user, comparing with the description of the events
		// TODO MORE ADVANCED: comparing with the interests of the other attending users
		return null;
	}

	private Float matchHistory(User user, Event event) {
		// TODO match using the history of previous events;
		return null;
	}

	private Float matchKeywordMap(User user, Event event) {
		// TODO match using a keywordmap, this is also the matching mechanism that can be created by a user
		return null;
	}

	public MatchType insertOrUpdateMatchType(Long uid) {
		EntityManager mgr = EMF.get().createEntityManager();
		MatchTypeEndpoint mtep = new MatchTypeEndpoint();
		try {
			if (mgr.find(MatchType.class,
					this.getId()) != null) {
				mtep.updateMatchType(this);
			} else {
				mgr.persist(this);				
			}
		} finally {
			mgr.close();
		}
		return this;
	}
}
