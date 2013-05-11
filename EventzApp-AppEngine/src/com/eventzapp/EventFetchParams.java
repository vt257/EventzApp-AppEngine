package com.eventzapp;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;

@Entity
public class EventFetchParams {
	@Id
	private Long id;
	private String name;
	private List<Long> friendids_touse;
	private List<Long> friendlistids_touse;
	private List<Long> likeids_touse;
	private Date since;
	private Date until;
	private List<String> location;
	private List<Float> location_range;
	
	public EventFetchParams(Long id, String name, List<Long> friendids_touse,
			List<Long> friendlistids_touse, List<Long> likeids_touse,
			Date since, Date until, List<String> location,
			List<Float> location_range) {
		this.id = id;
		this.name = name;
		this.friendids_touse = friendids_touse;
		this.friendlistids_touse = friendlistids_touse;
		this.likeids_touse = likeids_touse;
		this.since = since;
		this.until = until;
		this.location = location;
		this.location_range = location_range;
	}
	
	public EventFetchParams() {
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
	public List<Long> getFriendids_touse() {
		return friendids_touse;
	}
	public void setFriendids_touse(List<Long> friendids_touse) {
		this.friendids_touse = friendids_touse;
	}
	public List<Long> getFriendlistids_touse() {
		return friendlistids_touse;
	}
	public void setFriendlistids_touse(List<Long> friendlistids_touse) {
		this.friendlistids_touse = friendlistids_touse;
	}
	public List<Long> getLikeids_touse() {
		return likeids_touse;
	}
	public void setLikeids_touse(List<Long> likeids_touse) {
		this.likeids_touse = likeids_touse;
	}
	public Date getSince() {
		return since;
	}
	public void setSince(Date since) {
		this.since = since;
	}
	public Date getUntil() {
		return until;
	}
	public void setUntil(Date until) {
		this.until = until;
	}
	public List<String> getLocation() {
		return location;
	}
	public void setLocation(List<String> location) {
		this.location = location;
	}
	public List<Float> getLocation_range() {
		return location_range;
	}
	public void setLocation_range(List<Float> location_range) {
		this.location_range = location_range;
	}
	
	public EventFetchParams insertOrUpdateEventFetchParams(Long uid) {
		EntityManager mgr = EMF.get().createEntityManager();
		EventFetchParamsEndpoint efpep = new EventFetchParamsEndpoint();
		try {
			if (mgr.find(EventFetchParams.class, this.getId()) != null) {
				efpep.updateEventFetchParams(this);
			} else {
				mgr.persist(this);	
			}
		} finally {
			mgr.close();
		}
		return this;
	}
}
