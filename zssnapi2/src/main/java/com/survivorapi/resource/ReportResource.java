package com.survivorapi.resource;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.survivorapi.dto.ReportItemDTO;
import com.survivorapi.service.ReportService;



/**
 * REST controller for managing Report.
 * Created by Beatriz on 21/01/17.
 * 
 */
@RestController
@RequestMapping(value = "/report")
public class ReportResource {

    @Autowired
    private ReportService reportService;
    
    /**
     * List All Reports
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getReports() {
    	
    	String report = "RELATÓRIO GERAL:\n"+
    			reportService.getPercentageInfectedSurvivors()+"\n"+
    			reportService.getPercentageNonInfectedSurvivors()+"\n"+
    			"Quantide de itens por sobreviventes \n "+
    	reportService.getAverageResourceBySurvivor()+"\n"+
    	reportService.getLostPointInfectedSurvivors()+"\n";
        return report;
    }

    /**
     * Get Percentage Infected Survivor
     * @return
     */
    @RequestMapping(value = "/percentage-infected-survivors", method = RequestMethod.GET)
    public String getPercentageInfectedSurvivors() {
        return reportService.getPercentageInfectedSurvivors();
    }

    /**
     * Get Percentage non Infected Survivor
     * @return
     */
    @RequestMapping(value = "/percentage-non-infected-survivors", method = RequestMethod.GET)
    public String getPercentageNonInfectedSurvivors() {
        return reportService.getPercentageNonInfectedSurvivors();
    }

    /**
     * Get Afverage Resource by survivor
     * @return
     */
    @RequestMapping(value = "/average-resource-by-survivor", method = RequestMethod.GET)
    public Set<ReportItemDTO> getAverageResourceBySurvivor() {
        return reportService.getAverageResourceBySurvivor();
    }

    /**
     * Get lost point infected survivors
     * @return
     */
    @RequestMapping(value = "/lost-point-infected-survivors", method = RequestMethod.GET)
    public String getLostPointInfectedSurvivors() {
        return reportService.getLostPointInfectedSurvivors();
    }
}
