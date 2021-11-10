package com.mutant.api.mapper;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.mutant.api.dto.AdnDto;
import com.mutant.api.model.Adn;


@Component
public class AdnMapper {

	public Adn createAdnTemplate(boolean isMutant, AdnDto adnDto) {
		Adn adn = new Adn();
		adn.setAdnChain(Arrays.toString(adnDto.getDna()));
		adn.setMutant(isMutant);

		return adn;

	}

}
