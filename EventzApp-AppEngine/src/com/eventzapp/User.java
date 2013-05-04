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
	private List<Long> friendids;
	private List<Long> likeids;
	// TODO the location shouldn't be a string
	// it should contain the name, latitude and longitude
	private String location;
	private String locationlatitude;
	private String locationlongitude;
	private Long totalmatchmethod_id;
	private Long eventfatchparams_id;
	private Integer orderpreference;
	private String accestoken;
	private Date modified;
	
	public User() {
	}
	
	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}
	public List<Long> getFriendids() {
		return friendids;
	}
	public void setFriendids(List<Long> friendids) {
		this.friendids = friendids;
	}
	public List<Long> getLikeids() {
		return likeids;
	}
	public void setLikeids(List<Long> likeids) {
		this.likeids = likeids;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLocationlatitude() {
		return locationlatitude;
	}
	public void setLocationlatitude(String locationlatitude) {
		this.locationlatitude = locationlatitude;
	}
	public String getLocationlongitude() {
		return locationlongitude;
	}
	public void setLocationlongitude(String locationlongitude) {
		this.locationlongitude = locationlongitude;
	}
	public Long getTotalmatchmethod_id() {
		return totalmatchmethod_id;
	}
	public void setTotalmatchmethod_id(Long totalmatchmethod_id) {
		this.totalmatchmethod_id = totalmatchmethod_id;
	}
	public Long getEventfatchparams_id() {
		return eventfatchparams_id;
	}
	public void setEventfatchparams_id(Long eventfatchparams_id) {
		this.eventfatchparams_id = eventfatchparams_id;
	}
	public Integer getOrderpreference() {
		return orderpreference;
	}
	public void setOrderpreference(Integer orderpreference) {
		this.orderpreference = orderpreference;
	}
	public String getAccestoken() {
		return accestoken;
	}
	public void setAccestoken(String accestoken) {
		this.accestoken = accestoken;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	public void attachExtras() {
		ArrayList<Long> friendIds = new ArrayList<Long>();
		ArrayList<Long> likeIds = new ArrayList<Long>();
		Batcher batcher = new FacebookBatcher(this.getAccestoken());
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
		this.setFriendids(friendIds);
		this.setLikeids(likeIds);		
	}
}
