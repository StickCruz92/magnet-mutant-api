package com.mutant.api.dto;

public class AdnDto {

	private String idDna;
	private String dnaChain;
	private String mutant;
	

	public AdnDto() {}


	public AdnDto(String idDna, String dnaChain, String mutant) {
		this.idDna = idDna;
		this.dnaChain = dnaChain;
		this.mutant = mutant;
	}
	
	
	/**
	 * @return the idDna
	 */
	public String getIdDna() {
		return idDna;
	}
	/**
	 * @param idDna the idDna to set
	 */
	public void setIdDna(String idDna) {
		this.idDna = idDna;
	}
	/**
	 * @return the dnaChain
	 */
	public String getDnaChain() {
		return dnaChain;
	}
	/**
	 * @param dnaChain the dnaChain to set
	 */
	public void setDnaChain(String dnaChain) {
		this.dnaChain = dnaChain;
	}
	/**
	 * @return the mutant
	 */
	public String getMutant() {
		return mutant;
	}
	/**
	 * @param mutant the mutant to set
	 */
	public void setMutant(String mutant) {
		this.mutant = mutant;
	}



}
