package com.mutant.api.util;

import com.github.javafaker.Faker;
import com.mutant.api.dto.AdnDto;
import com.mutant.api.dto.StatiAdnDto;
import com.mutant.api.model.Adn;

public class AdnFactory {

	private static final Faker falker = new Faker();
	
	
	public static Adn getAnyAdn() {
		return Adn.builder()
				.idAdn(falker.toString())
				.adnChain(falker.toString())
				.mutant(true)
				.build();
		
	}
	
	public static StatiAdnDto getAnyStatiAdnDto() {
		return StatiAdnDto.builder()
				.countHumanDna(falker.number().randomDigit())
				.countMutantDna(falker.number().randomDigit())
				.ratio(falker.number().randomDouble(2, 1, 999999999))
				.build();
	}
	/*AdnDto dna*/
	public static AdnDto getAnyAdnDtoMutant() {
		String[] dna = { "ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTQ" };
		return AdnDto.builder()
				.dna(dna)
				.build();
	}
	
	public static AdnDto getAnyAdnDtoHuman() {
		String[] dna = { "OIUUUY","OIUOIO","GJHKJH","KUKIUY","REWQRR","WFFDP" };
		return AdnDto.builder()
				.dna(dna)
				.build();
	}
	
	
	public static AdnDto getAnyAdnDtoMutantExist() {
		String[] dna = { "ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTV" };
		return AdnDto.builder()
				.dna(dna)
				.build();
	}
	
}
