package com.eventzapp;

import org.codehaus.jackson.annotate.JsonProperty;

public class FbFriendId {
	@JsonProperty("uid2")
	private Long uid;

	public FbFriendId() {
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

}
