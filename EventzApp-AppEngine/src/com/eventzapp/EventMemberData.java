package com.eventzapp;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * This Entity holds data about event members
 * @author Vahe Tshitoyan
 *
 */
@Entity
public class EventMemberData {
	@Id
	private Long id;
	private Long uid;
	private Long eid;
	private String rsvp_status;
	public EventMemberData() {
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getEid() {
		return eid;
	}
	public void setEid(Long eid) {
		this.eid = eid;
	}
	public String getRsvp_status() {
		return rsvp_status;
	}
	public void setRsvp_status(String rsvp_status) {
		this.rsvp_status = rsvp_status;
	}
}
