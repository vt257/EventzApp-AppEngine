package com.eventzapp;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	private Long uid;
	private List<Long> friendids;
	private List<Long> likeids;
	// TODO the location shouldn't be a string
	// it should have all the data that the location on facebook can have
	private String location;
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
}
