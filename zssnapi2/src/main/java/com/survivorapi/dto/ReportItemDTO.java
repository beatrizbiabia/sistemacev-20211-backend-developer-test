package com.survivorapi.dto;


import com.survivorapi.entity.Item;

/**
 * Classe usada para a geração dos Relatórios.
 * 
 * Created by Beatriz on 11/01/21.
 */
public class ReportItemDTO {

    private String item;

    private Double avgQuantity;

    public ReportItemDTO() {
    }

    public ReportItemDTO(String item, Double avgQuantity) {
        this.item = item;
        this.avgQuantity = avgQuantity;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Double getAvgQuantity() {
        return avgQuantity;
    }

    public void setAvgQuantity(Double avgQuantity) {
        this.avgQuantity = avgQuantity;
    }
    
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return "Item = "+ item +" Média = "+ avgQuantity+"\n";
    }
}
