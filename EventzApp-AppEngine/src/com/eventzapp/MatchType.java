package com.eventzapp;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MatchType {
	@Id
	private Long id;
	private String name;
	// the method to use when matching
	private String matchMethod;
	public MatchType() {
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
			case "friends":
				match.setMatch(matchFriends(user, event));
			case "newpeople":
				match.setMatch(matchNewPeople(user, event));
			case "flirty":
				match.setMatch(matchFlirty(user, event));
			case "location":
				match.setMatch(matchLocation(user, event));
			case "time":
				match.setMatch(matchTime(user, event));
			case "interests":
				match.setMatch(matchInterests(user, event));
			case "history":
				match.setMatch(matchHistory(user, event));
			case "keywordmap":
			case "default":
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
}
