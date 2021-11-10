package com.mutant.api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mutant.api.model.Adn;


@Repository
public interface AdnRepository extends MongoRepository<Adn, String> {

	boolean existsAdnByAdnChain(String dnaChain);
		
	List<Adn> findByAdnChain(String adnChain);
	
}

