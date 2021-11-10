package com.mutant.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
@Builder
@Document(collection = "tbl_adn")
public class Adn {
	
	@Id
	private String idAdn;
	
	@Indexed(unique = true)
	private String adnChain;
	
	private boolean mutant;
	
	
	public Adn(String idAdn, String adnChain, boolean mutant) {
		this.idAdn = idAdn;
		this.adnChain = adnChain;
		this.mutant = mutant;
	}

	public Adn() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the idAdn
	 */
	public String getIdAdn() {
		return idAdn;
	}

	/**
	 * @param idAdn the idAdn to set
	 */
	public void setIdAdn(String idAdn) {
		this.idAdn = idAdn;
	}

	/**
	 * @return the adnChain
	 */
	public String getAdnChain() {
		return adnChain;
	}

	/**
	 * @param adnChain the adnChain to set
	 */
	public void setAdnChain(String adnChain) {
		this.adnChain = adnChain;
	}

	/**
	 * @return the mutant
	 */
	public boolean isMutant() {
		return mutant;
	}

	/**
	 * @param mutant the mutant to set
	 */
	public void setMutant(boolean mutant) {
		this.mutant = mutant;
	}

}
