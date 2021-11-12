package com.mutant.api.mapper;

import org.springframework.stereotype.Component;

import com.mutant.api.dto.AdnDto;
import com.mutant.api.model.Adn;

@Component
public class AdnMapper {
	

	public Adn createAdnDtoTemplateAdn(AdnDto adnDto, boolean isMutant) {
		var adn = new Adn();		
		adn.setAdnChain(adnDto.getDnaChain());
		adn.setIdAdn(adnDto.getIdDna());
		adn.setMutant(isMutant);

		return adn;
	}
	
	public AdnDto createAdnTemplateAdnDto(Adn adn) {
        
		var adnDto = new AdnDto();		
		adnDto.setDnaChain(adn.getAdnChain());
		adnDto.setIdDna(adn.getIdAdn());
		adnDto.setMutant((adn.isMutant()) ? "Eres un mutante" : "Eres un humano" );

		return adnDto;
	}

}
