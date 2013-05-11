package com.eventzapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.googlecode.batchfb.Batcher;
import com.googlecode.batchfb.FacebookBatcher;
import com.googlecode.batchfb.Later;

@Entity
public class User {
	@Id
	private Long uid;
	private List<Long> friendIds;
	private List<Long> likeIds;
	// TODO the location shouldn't be a string
	// it should contain the name, latitude and longitude
	private String location;
	private Double locationLatitude;
	private Double locationLongitude;
	private Long totalMatchMethodId;
	private Long eventFetchParamsId;
	private Integer orderPreference;
	private String accesToken;
	private Date modified;
	
	public User() {
	}
	
	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}
	public List<Long> getFriendIds() {
		return friendIds;
	}
	public void setFriendIds(List<Long> friendids) {
		this.friendIds = friendids;
	}
	public List<Long> getLikeIds() {
		return likeIds;
	}
	public void setLikeIds(List<Long> likeids) {
		this.likeIds = likeids;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Double getLocationLatitude() {
		return locationLatitude;
	}
	public void setLocationLatitude(Double locationlatitude) {
		this.locationLatitude = locationlatitude;
	}
	public Double getLocationLongitude() {
		return locationLongitude;
	}
	public void setLocationLongitude(Double locationlongitude) {
		this.locationLongitude = locationlongitude;
	}
	public Long getTotalMatchMethodId() {
		return totalMatchMethodId;
	}
	public void setTotalMatchMethodId(Long totalmatchmethod_id) {
		this.totalMatchMethodId = totalmatchmethod_id;
	}
	public Long getEventFetchParamsId() {
		return eventFetchParamsId;
	}
	public void setEventFetchParamsId(Long eventfatchparams_id) {
		this.eventFetchParamsId = eventfatchparams_id;
	}
	public Integer getOrderPreference() {
		return orderPreference;
	}
	public void setOrderPreference(Integer orderpreference) {
		this.orderPreference = orderpreference;
	}
	public String getAccesToken() {
		return accesToken;
	}
	public void setAccesToken(String accestoken) {
		this.accesToken = accestoken;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	
	
	public Gender getGender() {
		//TODO: Get the user's gender from very beginning
		
		return Gender.male;
	}
	
	public void attachExtras() {
		ArrayList<Long> friendIds = new ArrayList<Long>();
		ArrayList<Long> likeIds = new ArrayList<Long>();
		Batcher batcher = new FacebookBatcher(this.getAccesToken());
		Later<List<FbFriendId>> userFriends = batcher.query(
			    "SELECT uid2 FROM friend WHERE uid1 = " + this.getUid(), FbFriendId.class);
		Later<List<FbLike>> userLikes = batcher.query("SELECT page_id FROM page_fan WHERE uid = " + "1388591541", FbLike.class);
		//TODO change the uid to the user's uid
		userLikes.get();
		for (FbFriendId friend : userFriends.get()) {
			friendIds.add(friend.getUid());
		}
		for (FbLike like : userLikes.get()) {
			likeIds.add(like.getId());
		}
		this.setFriendIds(friendIds);
		this.setLikeIds(likeIds);		
	}
	
	/**
	 * Method that inserts the default match types for the user
	 * If the matchtype has an ancestor of a type user it belongs only to this user
	 * If the matchtype doesn't have an ancestor it is public and can be used by every user
	 * This method inserts the public matchtypes
	 */
	public void insertDefaultMatchTypes() {
		List<MatchType> defaultMatchTypes = new ArrayList<MatchType>();
		Long uid = this.getUid();
		defaultMatchTypes.add(new MatchType(uid, MatchType.FRIENDS_MATCH_NAME, MatchType.FRIENDS_MATCH_METHOD));
		defaultMatchTypes.add(new MatchType(uid, MatchType.NEWPEOPLE_MATCH_NAME, MatchType.NEWPEOPLE_MATCH_METHOD));
		defaultMatchTypes.add(new MatchType(uid, MatchType.FLIRTY_MATCH_NAME, MatchType.FLIRTY_MATCH_METHOD));
		defaultMatchTypes.add(new MatchType(uid, MatchType.LOCATION_MATCH_NAME, MatchType.LOCATION_MATCH_METHOD));
		defaultMatchTypes.add(new MatchType(uid, MatchType.TIME_MATCH_NAME, MatchType.TIME_MATCH_METHOD));
		defaultMatchTypes.add(new MatchType(uid, MatchType.HISTORY_MATCH_NAME, MatchType.HISTORY_MATCH_METHOD));
		defaultMatchTypes.add(new MatchType(uid, MatchType.PARTY_DEFAULT_MATCH_NAME, MatchType.KEYWORDMAP_MATCH_METHOD));
		defaultMatchTypes.add(new MatchType(uid, MatchType.WORKEDUCATIONBUSINESS_DEFAULT_MATCH_NAME, MatchType.KEYWORDMAP_MATCH_METHOD));
		defaultMatchTypes.add(new MatchType(uid, MatchType.CAUSE_DEFAULT_MATCH_NAME, MatchType.KEYWORDMAP_MATCH_METHOD));
		for (MatchType mt : defaultMatchTypes) {
			mt.insertOrUpdateMatchType(uid);
		}
	}
	
	/**
	 * The method to insert the default total match method
	 * again, if it doesn't have an ancestor of a type user it can be used by all the users
	 * this method inserts the default one, and thus the one without any user ancestor 
	 */
	public void insertDefaultTotalMatchMethod() {
		// TODO implement this method
		TotalMatchMethod totalMatchMthd = new TotalMatchMethod(totalMatchMethodId, "Default Total");
		totalMatchMthd.insertDefaultTotalMatchMethodContribMapItems();
	}
	
	/**
	 * the method to insert the default fetch params
	 * again as previous two method takes care of the ancestors the same way
	 * this method should insert an EventFetchParams without any ancestors 
	 * because these are the default ones and should work for everybody
	 */
	public void insertDefaultEventFetchParams() {
		// TODO implement this method
		List<Long> friendlistids_touse = new ArrayList<Long>(); //Empty list by default
		Date since = new Date(); //Now by default
		Date until = new Date(Long.MAX_VALUE); //End of time by default
		List<String> locations = new ArrayList<String>() {{add(location);}}; //Users location by default
		List<Float> location_range = new ArrayList<Float>() {{add(new Float(50));}}; //TODO: remove the hard typed 50
		EventFetchParams defaultEventFetchParams = new EventFetchParams(eventFetchParamsId, "default", friendIds, friendlistids_touse, likeIds, 
																		since, until, locations, location_range);
		defaultEventFetchParams.insertOrUpdateEventFetchParams(uid);
	}
}
