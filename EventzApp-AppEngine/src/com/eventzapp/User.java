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
	private String locationLatitude;
	private String locationLongitude;
	private Long totalMatchMethodId;
	private Long eventFatchParamsId;
	private Integer orderPreference;
	private String accesToken;
	private Date modified;
	private final List<Match> matchTypes = new ArrayList<Match>();
	
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
	public String getLocationLatitude() {
		return locationLatitude;
	}
	public void setLocationLatitude(String locationlatitude) {
		this.locationLatitude = locationlatitude;
	}
	public String getLocationLongitude() {
		return locationLongitude;
	}
	public void setLocationLongitude(String locationlongitude) {
		this.locationLongitude = locationlongitude;
	}
	public Long getTotalMatchMethodId() {
		return totalMatchMethodId;
	}
	public void setTotalMatchMethodId(Long totalmatchmethod_id) {
		this.totalMatchMethodId = totalmatchmethod_id;
	}
	public Long getEventFatchParamsId() {
		return eventFatchParamsId;
	}
	public void setEventFatchParamsId(Long eventfatchparams_id) {
		this.eventFatchParamsId = eventfatchparams_id;
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
	
	public List<Match> getMatchType() {
		return matchTypes;
	}
	
	
	public void attachExtras() {
		ArrayList<Long> friendIds = new ArrayList<Long>();
		ArrayList<Long> likeIds = new ArrayList<Long>();
		Batcher batcher = new FacebookBatcher(this.getAccesToken());
		Later<List<FbFriendId>> userFriends = batcher.query(
			    "SELECT uid2 FROM friend WHERE uid1 = " + this.getUid(), FbFriendId.class);
		Later<List<FbLike>> userLikes = batcher.query("SELECT page_id FROM page_fan WHERE uid = " + "1388591541", FbLike.class);
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
}
