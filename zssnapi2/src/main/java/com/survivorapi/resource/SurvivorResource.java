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

import com.survivorapi.entity.Survivor;
import com.survivorapi.repository.SurvivorRepository;

/**
 * REST controller for managing Survivor.
 * Created by Beatriz on 11/01/21.
 */
@RestController
@RequestMapping("/survivor-management")
public class SurvivorResource {

	private static final String ENTITY_NAME = "survivor";
	
	public SurvivorResource() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private SurvivorRepository repository;

	public SurvivorRepository getRepository() {
		return repository;
	}

	/**
	 * List All Survivors
	 * @return List<Survivor>
	 */
	@GetMapping(value = "/survivors")
	public List<Survivor> getAllSurvivor() {
		return repository.findAll();
	}

	/**
	 * Create a new Survivor
	 * @param newSurvivor
	 * @return
	 */
	@PostMapping("/survivors")
	Survivor createOrSaveSurvivor(@RequestBody Survivor newSurvivor) {
		newSurvivor.setInfected(false);
		return repository.save(newSurvivor);
	}

	/**
	 * 
	 * Find survivor by ID
	 * @param id
	 * @return
	 */
	@GetMapping("/survivors/{id}")
	Survivor getSurvivorById(@PathVariable Long id) {
		return repository.findById(id).get();
	}

	/**
	 * Update Survivor
	 * @param newSurvivor
	 * @param id
	 * @return
	 */
	@PutMapping("/survivors/{id}")
	Survivor updateSurvivor(@RequestBody Survivor newSurvivor, @PathVariable Long id) {
		
		Survivor s = repository.findById(id).get();

		return repository.findById(id).map(survivor -> {
			if(!survivor.isContamined()) {
				
			
			survivor.setName(newSurvivor.getName());
			survivor.setAge(newSurvivor.getAge());
			survivor.setGender(newSurvivor.getGender());
			survivor.setLastLocation(newSurvivor.getLastLocation());
			survivor.setItens(survivor.getItens());
			//survivor.setInventory(newSurvivor.getInventory());
			survivor.setReporteds(newSurvivor.getReporteds());
			}
			return repository.save(survivor);
		}).orElseGet(() -> {
			newSurvivor.setId(id);
			return repository.save(newSurvivor);
		});
	}

	/**
	 * Delete Survivor
	 * @param id
	 */
	@DeleteMapping("/survivors/{id}")
	void deleteSurvivor(@PathVariable Long id) {
		repository.deleteById(id);
	}

	
}
