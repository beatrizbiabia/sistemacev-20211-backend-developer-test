package com.survivorapi.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.survivorapi.dto.ReportItemDTO;
import com.survivorapi.entity.Item;
import com.survivorapi.entity.Survivor;
import com.survivorapi.repository.ReportRepository;
import com.survivorapi.repository.SurvivorRepository;


/**
 * Class Service for Reports
 *  Created by Beatriz on 11/01/21.
 */
@Service
public class ReportService {

 //   @Autowired
   // private ReportRepository reportRepository;
    
    @Autowired
    private SurvivorRepository survivorRepository;

//    @Transactional(readOnly = true)
//    public Double getPercentageInfectedSurvivors() {
//        return reportRepository.getPercentageSurvivorsByStatus(Boolean.TRUE);
//    }
    /**
     * Get Percentage Infected Survivor
     * @return
     */
    @Transactional(readOnly = true)
    public String getPercentageInfectedSurvivors() {
    	
    	List<Survivor> listSurvivor = survivorRepository.findAll();
    	
    	double quantityInfected = 0;
    	
    	
    	for(Survivor survivor : listSurvivor) {
    		
    		if(survivor.isContamined()) {
    			quantityInfected += 1;
    		}
    	}
    	Double percentageInfected = ((quantityInfected *100)/listSurvivor.size());
    	
    	
    	
        return "Porcentagem de Infectados = " + percentageInfected + "%";
    }
    
    /**
     * Get Percentage non Infected Survivor
     * @return
     */
    @Transactional(readOnly = true)
    public String getPercentageNonInfectedSurvivors() {
    	
    	List<Survivor> listSurvivor = survivorRepository.findAll();
    	
    	double quantityNotInfected =0;
    	
    	for(Survivor survivor : listSurvivor) {
    		
    		if(!survivor.isContamined()) {
    		 			quantityNotInfected+=1;
    		}
    	}
    	Double percentageNotInfected = ((quantityNotInfected *100)/listSurvivor.size());
   	
    	
        return "Porcentagem de não ifectados = "+percentageNotInfected+"%";
    }

//    @Transactional(readOnly = true)
//    public Double getPercentageNonInfectedSurvivors() {
//        return reportRepository.getPercentageSurvivorsByStatus(Boolean.FALSE);
//    }

    /**
     * Get Average Resource by Survivor
     * @return
     */
    @Transactional(readOnly = true)
    public Set<ReportItemDTO> getAverageResourceBySurvivor() {
    	
    	List<Survivor> listSurvivor = survivorRepository.findAll();
    	
    	//total de Survivors não infectados
    	int totalSurvivor = 0;
    	
    	//Quantidade dos itens
    	int quantityWater =0;
    	int quantityFood=0;
    	int quantityMedication =0;
    	int quantityAmmunition =0;
    	
    	ReportItemDTO reportWater = new ReportItemDTO();
    	ReportItemDTO reportFood = new ReportItemDTO();
    	ReportItemDTO reportMedication = new ReportItemDTO();
    	ReportItemDTO reportAmmunition = new ReportItemDTO();
    	
    	for(Survivor survivor : listSurvivor) {
    		
    		if(!survivor.isContamined()) {
    			totalSurvivor+=1;
    			//Somatorio da quantidade de itens
    			for(Item item : survivor.getItens()) {
    				
    				switch (item.getName()) {
					case "water":
						quantityWater +=item.getQuantity();
						reportWater.setItem("water");
						
					break;
					case "food":
						quantityFood+=item.getQuantity();
						reportFood.setItem("food");
						break;	
					case "medication":
						quantityMedication+=item.getQuantity();
						reportMedication.setItem("medication");
						break;	
					case "ammunition":
						quantityAmmunition+=item.getQuantity();
						reportAmmunition.setItem("ammunition");
						break;	
					default:
						break;
					}
    			}
    			
    		}
    		
    	}
    
     Set<ReportItemDTO> reportedItemDTO = new HashSet<ReportItemDTO>();	
     double	averageWater = quantityWater/totalSurvivor;
     reportWater.setAvgQuantity(averageWater);
     reportedItemDTO.add(reportWater);
         
     double averageFood = quantityFood/totalSurvivor;
     reportFood.setAvgQuantity(averageFood);
     reportedItemDTO.add(reportFood);
     
     double averageMedication = quantityMedication/totalSurvivor;
     reportMedication.setAvgQuantity(averageMedication);
     reportedItemDTO.add(reportMedication);
     
     double averageAmmunition = quantityAmmunition/totalSurvivor;
     reportAmmunition.setAvgQuantity(averageAmmunition);
     reportedItemDTO.add(reportAmmunition);
    	
       
     
    	
    	
        return reportedItemDTO;
    }

    /**
     * Gel lost Point for Infected Survivor
     * @return
     */
    @Transactional(readOnly = true)
    public String getLostPointInfectedSurvivors() {
    	
    	List<Survivor> listSurvivor = survivorRepository.findAll();
    	
    	int quantityPointWater =0;
    	int quantityPointFood=0;
    	int quantityPointMedication =0;
    	int quantityPointAmmunition =0;
    	
    	for(Survivor survivor : listSurvivor) {
    		
    		if(survivor.isContamined()) {
    			
    			for(Item item : survivor.getItens()) {
    				
    				switch (item.getName()) {
					case "water":
						quantityPointWater +=item.getPoint();
						
						
					break;
					case "food":
						quantityPointFood+=item.getPoint();
					
						break;	
					case "medication":
						quantityPointMedication+=item.getPoint();
						
						break;	
					case "ammunition":
						quantityPointAmmunition+=item.getPoint();
						
						break;	
					default:
						break;
					}
    			}
    			
    		}
    		
    	}
    	//Total geral de pontos perdidos
    	int totalPoint = quantityPointWater + quantityPointFood + quantityPointMedication+quantityPointAmmunition;
    	
    	String pointLost = "Total de pontos perdidos: \n Water: "+ quantityPointWater + "\n Food: "+quantityPointFood+"\n Medication: "+quantityPointMedication+
    			           "\n Ammunition: " + quantityPointAmmunition+ " \n Total Geral: "+ totalPoint;
        return pointLost;
    }
}
