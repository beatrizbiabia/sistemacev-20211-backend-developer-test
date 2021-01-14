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

import com.survivorapi.entity.ContaminationFlag;
import com.survivorapi.entity.Location;
import com.survivorapi.entity.Survivor;
import com.survivorapi.repository.ContaminationFlagRepository;
import com.survivorapi.repository.ContaminationFlagRepository;
import com.survivorapi.repository.LocationRepository;
import com.survivorapi.repository.SurvivorRepository;

/**
 * REST controller for managing Survivor.
 *  Created by Beatriz on 11/01/21.
 */
@RestController
@RequestMapping("/survivor-management")
public class ContaminationFlagResource {

	private static final String ENTITY_NAME = "contaminationFlag";

	@Autowired
	private ContaminationFlagRepository repository;

	public ContaminationFlagRepository getRepository() {
		return repository;
	}

	/**
	 * Obtém Lista total de ContaminationFlag
	 * @return List<ContaminationFlag>
	 */
	@GetMapping(value = "/contaminationFlags")
	public List<ContaminationFlag> getAllContaminationFlag() {
		return repository.findAll();
	}

	/**
	 * A new Contaminatios=n Flaf
	 * @param newContaminationFlag
	 * @return
	 */
	@PostMapping("/contaminationFlags")
	ContaminationFlag createOrSaveContaminationFlag(@RequestBody ContaminationFlag newContaminationFlag) {
		return repository.save(newContaminationFlag);
	}

	/**
	 * Find Contamination Flag by Id
	 * @param id
	 * @return
	 */
	@GetMapping("/contaminationFlags/{id}")
	ContaminationFlag getContaminationFlagById(@PathVariable Long id) {
		return repository.findById(id).get();
	}

	/**
	 * Upadate a Contamination Flag
	 * @param newContaminationFlag
	 * @param id
	 * @return
	 */
	@PutMapping("/contaminationFlags/{id}")
	ContaminationFlag updateContaminationFlag(@RequestBody ContaminationFlag newContaminationFlag, @PathVariable Long id) {

		return repository.findById(id).map(contaminationFlag -> {
			contaminationFlag.setReported(newContaminationFlag.getReported());
			contaminationFlag.setReportedBy(newContaminationFlag.getReportedBy());
			
			return repository.save(contaminationFlag);
		}).orElseGet(() -> {
			newContaminationFlag.setId(id);
			return repository.save(newContaminationFlag);
		});
	}

	/**
	 * Delete a Contamination Flag
	 * @param id
	 */
	@DeleteMapping("/contaminationFlags/{id}")
	void deleteContaminationFlag(@PathVariable Long id) {
		repository.deleteById(id);
	}

	
}
