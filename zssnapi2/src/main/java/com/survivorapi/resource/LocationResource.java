package com.survivorapi.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.survivorapi.entity.Location;
import com.survivorapi.entity.Survivor;
import com.survivorapi.repository.LocationRepository;
import com.survivorapi.repository.SurvivorRepository;

/**
 * REST controller for managing Survivor.
 * Created by Beatriz on 11/01/21.
 */
@RestController
@RequestMapping("/survivor-management")
public class LocationResource {

	private static final String ENTITY_NAME = "location";

	@Autowired
	private LocationRepository repository;

	public LocationRepository getRepository() {
		return repository;
	}

	/**
	 * Get all Location
	 * @return
	 */
	@GetMapping(value = "/locations")
	public List<Location> getAllLocation() {
		return repository.findAll();
	}

	/**
	 * Create a new location
	 * @param newLocation
	 * @return
	 */
	@PostMapping("/locations")
	Location createOrSaveLocation(@RequestBody Location newLocation) {
		return repository.save(newLocation);
	}

	/**
	 * Get Location by id
	 * @param id
	 * @return
	 */
	@GetMapping("/locations/{id}")
	Location getLocationById(@PathVariable Long id) {
		return repository.findById(id).get();
	}

	/**
	 * Update Location
	 * @param newLocation
	 * @param id
	 * @return
	 */
	@PutMapping("/locations/{id}")
	Location updateLocation(@RequestBody Location newLocation, @PathVariable String id) {

		
		return repository.findById(Long.parseLong(id)).map(location -> {
			location.setLatitude(newLocation.getLatitude());  
			location.setLongitude(newLocation.getLongitude());  
			return repository.save(location);
		}).orElseGet(() -> {
			newLocation.setId(Long.parseLong(id));
			return repository.save(newLocation);
		});
	}

	/**
	 * Delete Location
	 * @param id
	 */
	@DeleteMapping("/locations/{id}")
	void deleteLocation(@PathVariable Long id) {
		repository.deleteById(id);
	}

	
}
