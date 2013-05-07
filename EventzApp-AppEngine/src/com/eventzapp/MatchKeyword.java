package com.eventzapp;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public final class MatchKeyword {
	@Id
	private String id;
	private String keyword;
	private Float weight;
	public MatchKeyword() {
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Float getWeight() {
		return weight;
	}
	public void setWeight(Float weight) {
		this.weight = weight;
	}
}
