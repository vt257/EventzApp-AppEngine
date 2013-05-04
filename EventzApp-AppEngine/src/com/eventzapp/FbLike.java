package com.eventzapp;

import org.codehaus.jackson.annotate.JsonProperty;

public class FbLike {
	@JsonProperty("page_id")
	private Long id;

	public FbLike() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
