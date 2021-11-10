package com.mutant.api.dto;

import lombok.Builder;


@Builder
public class AdnDto {

   private String id;	
   private String[] dna;
   private boolean isMutant;
   
   public AdnDto() {}
   
   public AdnDto(String id, String[] dna, boolean isMutant) {
		this.id = id;
		this.dna = dna;
		this.isMutant = isMutant;
	}
		
   
   

/**
 * @return the id
 */
public String getId() {
	return id;
}
/**
 * @param id the id to set
 */
public void setId(String id) {
	this.id = id;
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
/**
 * @return the isMutant
 */
public boolean isMutant() {
	return isMutant;
}
/**
 * @param isMutant the isMutant to set
 */
public void setMutant(boolean isMutant) {
	this.isMutant = isMutant;
} 
   
   
}
