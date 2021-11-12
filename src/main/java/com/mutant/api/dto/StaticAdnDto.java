package com.mutant.api.dto;

import lombok.Builder;


@Builder
public class StaticAdnDto {
	
	private int countMutantDna;
	private int countHumanDna;
	private double ratio;
	
	/**
	 * @param ratio the ratio to set
	 */
	public void setRatio(double ratio) {
		this.ratio = ratio;
	}
	public StaticAdnDto(int countMutantDna, int countHumanDna, double ratio) {
		this.countMutantDna = countMutantDna;
		this.countHumanDna = countHumanDna;
		this.ratio = ratio;
	}
	
	public StaticAdnDto() {
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * @return the countMutantDna
	 */
	public int getCountMutantDna() {
		return countMutantDna;
	}
	/**
	 * @param countMutantDna the countMutantDna to set
	 */
	public void setCountMutantDna(int countMutantDna) {
		this.countMutantDna = countMutantDna;
	}
	/**
	 * @return the countHumanDna
	 */
	public int getCountHumanDna() {
		return countHumanDna;
	}
	/**
	 * @param countHumanDna the countHumanDna to set
	 */
	public void setCountHumanDna(int countHumanDna) {
		this.countHumanDna = countHumanDna;
	}
	/**
	 * @return the ratio
	 */
	public double getRatio() {
		return ratio;
	}

	
	
}
