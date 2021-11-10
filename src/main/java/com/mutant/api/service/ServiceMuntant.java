package com.mutant.api.service;

import java.util.List;

import com.mutant.api.dto.AdnDto;
import com.mutant.api.model.Adn;
import com.mutant.api.util.Response;

public interface ServiceMuntant {

	boolean isMutant(String[] dna);
	
	Response validateMutant(AdnDto dna);

	Response getListAdn();
	
	boolean existsByAdnChain(String dnaChain);
			
	Response getStats();
	
	List<Adn> getFindByAdnChain(String adn);
}
