package com.mutant.api.dto;

import lombok.Builder;

@Builder
public class DnaDto {
	
	private String[] dna;
	
	
	public DnaDto() {
	}

	public DnaDto(String[] dna) {
		this.dna = dna;
	}

	/**
	 * @return the dna
	 */
	public String[] getDna() {
		return dna;
	}

	/**
	 * @param dna the dna to set
	 */
	public void setDna(String[] dna) {
		this.dna = dna;
	}

	
}
