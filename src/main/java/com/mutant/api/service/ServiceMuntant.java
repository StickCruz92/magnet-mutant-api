package com.mutant.api.service;

import java.util.List;

import com.mutant.api.dto.DnaDto;
import com.mutant.api.model.Adn;
import com.mutant.api.util.Response;

public interface ServiceMuntant {
	
	Response validateMutant(DnaDto dna);

	Response getListAdn();
	
	boolean existsByAdnChain(String dnaChain);
			
	Response getStats();
	
	List<Adn> getFindByAdnChain(String adn);
}
