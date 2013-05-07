package com.eventzapp;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
/**
 * The basic definition of the total match method which defines how the matches of
 * different matchtypes should be combined to calcualte the total match
 * @author Vahe Tshitoyan
 *
 */
public class TotalMatchMethod {
	@Id
	private Long id;
	private String name;
	
	public TotalMatchMethod() {
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
	
	/**
	 * function to isert the default contrib map which should have
	 * the default totalmatchmethod as its ancestor
	 */
	public void insertDefaultTotalMatchMethodContribMapItems() {
		// TODO implement this method
	}
}
