package com.eventzapp;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.codehaus.jackson.annotate.JsonProperty;

import com.google.appengine.api.datastore.Text;

@Entity
public class Event {
	@Id
	@JsonProperty("eid")
	private Long eid;
	@JsonProperty("name")
	private String name;
	@JsonProperty("description")
	private Text description;
	@JsonProperty("location")
	private String location;
	@JsonProperty("start_time")
	private Date start_time;
	@JsonProperty("timezone")
	private String timezone;
	@JsonProperty("attending_count")
	private Integer goingnumber;
	@JsonProperty("unsure_count")
	private Integer unsurenumber;
	@JsonProperty("not_replied_count")
	private Integer invitednumber;
	private Float malepercent;
	private List<Long> uids;
	public Event() {
	}
	public Long getEid() {
		return eid;
	}
	public void setEid(Long eid) {
		this.eid = eid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Text getDescription() {
		return description;
	}
	public void setDescription(Text description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Date getStart_time() {
		return start_time;
	}
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public Integer getGoingnumber() {
		return goingnumber;
	}
	public void setGoingnumber(Integer goingnumber) {
		this.goingnumber = goingnumber;
	}
	public Integer getUnsurenumber() {
		return unsurenumber;
	}
	public void setUnsurenumber(Integer unsurenumber) {
		this.unsurenumber = unsurenumber;
	}
	public Integer getInvitednumber() {
		return invitednumber;
	}
	public void setInvitednumber(Integer invitednumber) {
		this.invitednumber = invitednumber;
	}
	public Float getMalepercent() {
		return malepercent;
	}
	public void setMalepercent(Float malepercent) {
		this.malepercent = malepercent;
	}
	public List<Long> getUids() {
		return uids;
	}
	public void setUids(List<Long> uids) {
		this.uids = uids;
	}
	
}
