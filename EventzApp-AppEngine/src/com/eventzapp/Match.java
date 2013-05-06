package com.eventzapp;

import java.util.List;

public final class Match {
	
	private final String id;
	private String name;
	private List<Keyword> keywordMap;
	
	public Match(String id, String name, List<Keyword> keywordMap) {
		this.id = id;
		this.name = name;
		this.keywordMap = keywordMap;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public List<Keyword> getKeywordMap() {
		return keywordMap;
	}
	
	


}
