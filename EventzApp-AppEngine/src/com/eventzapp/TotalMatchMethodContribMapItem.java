package com.eventzapp;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The contribution map belongint to a totalmatchMethod
 * SHOULD HAVE an ancestor of a type TotalMatchMethod
 * @author Vahe Tshitoyan
 *
 */
@Entity
public class TotalMatchMethodContribMapItem {
	@Id
	private Long Id;
	private Long matchTypeId;
	private Float contribution;
	public TotalMatchMethodContribMapItem() {
	}
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public Long getMatchTypeId() {
		return matchTypeId;
	}
	public void setMatchTypeId(Long matchTypeId) {
		this.matchTypeId = matchTypeId;
	}
	public Float getContribution() {
		return contribution;
	}
	public void setContribution(Float contribution) {
		this.contribution = contribution;
	}

}
