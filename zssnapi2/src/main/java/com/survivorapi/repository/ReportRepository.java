package com.survivorapi.repository;


import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.survivorapi.dto.ReportItemDTO;
import com.survivorapi.entity.Survivor;

/**
 * Spring Data JPA repository for the Report/Survivor entity.
 * Created by Beatriz on 11/01/21.
 */
@Repository
public interface ReportRepository extends JpaRepository<Survivor, Long> {


 //   Double getPercentageSurvivorsByStatus();


 //   Set<ReportItemDTO> getAverageResourceBySurvivor();


 //   Long getLostPointInfectedSurvivors();
}
