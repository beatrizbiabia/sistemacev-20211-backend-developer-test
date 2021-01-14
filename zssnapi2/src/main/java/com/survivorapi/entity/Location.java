/**
 * 
 */
package com.survivorapi.entity;

import javax.persistence.*;


import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Beatriz Almeida Sobrinho em 11/01/21
 * 
 * A Location.
 */
@Entity
@Table(name = "location")
public class Location implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Longitude não pode ser nulo")
	@Column(name = "longitude", nullable = false)
	private String longitude;

	@NotNull(message = "Latitude não pode ser nulo")
	@Column(name = "latitude", nullable = false)
	private String latitude;

	/**
	 * Only to frameworks
	 */
	@Deprecated
	public Location() {
	}

	public Location(String longitude, String latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLongitude() {
		return longitude;
	}

	public Location longitude(String longitude) {
		this.longitude = longitude;
		return this;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public Location latitude(String latitude) {
		this.latitude = latitude;
		return this;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Location location = (Location) o;
		if (location.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), location.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "Location{" + "id=" + getId() + ", longitude='" + getLongitude() + "'" + ", latitude='" + getLatitude()
				+ "'" + "}";
	}

}
