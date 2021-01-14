/**
 * 
 */
package com.survivorapi.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 *@author Beatriz Almeida Sobrinho em 11/01/21
 *
 *Trade Itens
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EPoint {
	
	WATER(1, "Water", 4),

    FOOD(2, "Food", 3),

    MEDICATION(3, "Medication", 2),

    AMMUNITION(4, "Ammunition", 1);

	 private Integer id;

	 private String name;

	 private Integer points;

	 EPoint(Integer id, String name, Integer points) {
	        this.id = id;
	        this.name = name;
	        this.points = points;
	    }

	    @JsonCreator
	    public static EPoint lookup(final Integer id) {
	        for (final EPoint enumeration : values()) {
	            if (enumeration.getId().equals(id)) {
	                return enumeration;
	            }
	        }
	        return null;
	    }

	    public Integer getId() {
	        return id;
	    }

	    public void setId(Integer id) {
	        this.id = id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public Integer getPoints() {
	        return points;
	    }

	    public void setPoints(Integer points) {
	        this.points = points;
	    }

}
