package com.mutant.api.util;

import com.github.javafaker.Faker;
import com.mutant.api.dto.DnaDto;
import com.mutant.api.dto.StaticAdnDto;
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
	
	public static StaticAdnDto getAnyStatiAdnDto() {
		return StaticAdnDto.builder()
				.countHumanDna(falker.number().randomDigit())
				.countMutantDna(falker.number().randomDigit())
				.ratio(falker.number().randomDouble(2, 1, 999999999))
				.build();
	}
	/*AdnDto dna*/
	public static DnaDto getAnyAdnDtoMutant() {
		String[] dna = { "ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTQ" };
		return DnaDto.builder()
				.dna(dna)
				.build();
	}
	
	public static DnaDto getAnyAdnDtoHuman() {
		String[] dna = { "OIUUUY","OIUOIO","GJHKJH","KUKIUY","REWQRR","WFFDP" };
		return DnaDto.builder()
				.dna(dna)
				.build();
	}
	
	
	public static DnaDto getAnyAdnDtoMutantExist() {
		String[] dna = { "ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTV" };
		return DnaDto.builder()
				.dna(dna)
				.build();
	}
	
}
