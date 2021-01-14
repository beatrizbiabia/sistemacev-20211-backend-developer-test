package com.survivorapi.repository;


import org.springframework.stereotype.Repository;

import com.survivorapi.entity.Survivor;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Survivor entity.
 *  Created by Beatriz on 11/01/21.
 */
//@SuppressWarnings("unused")
@Repository
public interface SurvivorRepository extends JpaRepository<Survivor, Long>, JpaSpecificationExecutor<Survivor> {
	

    Survivor findByName(String name);

}
