package com.eventzapp;

import javax.persistence.Entity;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Id;

/**
 * This Entity holds data about event members
 * @author Vahe Tshitoyan
 *
 */
@Entity
public class EventMemberData {
	@Id
	private String id;
	private Long uid;
	private Long eid;
	private String rsvp_status;
	public EventMemberData() {
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public EventMemberData insertOrUpdateEventMemberData(Long uid) {
		EntityManager mgr = EMF.get().createEntityManager();
		EventMemberDataEndpoint emde = new EventMemberDataEndpoint();
		try {
			if (mgr.find(EventMemberData.class,
					this.getId()) != null) {
				emde.updateEventMemberData(this);
			} else {
				mgr.persist(this);				
			}
		} finally {
			mgr.close();
		}
		return this;
	}
}
