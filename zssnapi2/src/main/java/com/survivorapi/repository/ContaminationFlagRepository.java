package com.survivorapi.repository;


import org.springframework.stereotype.Repository;

import com.survivorapi.entity.ContaminationFlag;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ContaminationFlag entity.
 * Created by Beatriz on 11/01/21.
 */
@SuppressWarnings("unused")
@Repository
public interface ContaminationFlagRepository extends JpaRepository<ContaminationFlag, Long>, JpaSpecificationExecutor<ContaminationFlag> {

}
