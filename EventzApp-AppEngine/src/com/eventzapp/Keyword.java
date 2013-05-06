package com.eventzapp;

public final class Keyword {
	
	private final String key;
	private final double value;

	public Keyword(String key, double value) {
		this.key = key;
		this.value = value;
	}

	public double getValue() {
		return value;
	}
	
	public String getKey() {
		return key;
	}

}
