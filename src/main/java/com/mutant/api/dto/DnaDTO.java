package com.mutant.api.dto;

public class DnaDTO {
	
	private String idAdn;
	
	private String adnChain;
	
	private boolean mutant;
	

	public DnaDTO() {
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
