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
	
}
