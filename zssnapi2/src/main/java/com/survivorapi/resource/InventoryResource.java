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

import com.survivorapi.entity.Inventory;
import com.survivorapi.entity.Location;
import com.survivorapi.entity.Survivor;
import com.survivorapi.repository.InventoryRepository;
import com.survivorapi.repository.InventoryRepository;
import com.survivorapi.repository.LocationRepository;
import com.survivorapi.repository.SurvivorRepository;

/**
 * REST controller for managing Survivor.
 * Created by Beatriz on 11/01/21.
 */
@RestController
@RequestMapping("/survivor-management")
public class InventoryResource {

	private static final String ENTITY_NAME = "inventory";

	@Autowired
	private InventoryRepository repository;

	public InventoryRepository getRepository() {
		return repository;
	}

	/**
	 * List all inventory
	 * @return
	 */
	@GetMapping(value = "/inventories")
	public List<Inventory> getAllInventory() {
		return repository.findAll();
	}

	/**
	 * create a inventory
	 * @param newInventory
	 * @return
	 */
	@PostMapping("/inventories")
	Inventory createOrSaveInventory(@RequestBody Inventory newInventory) {
		return repository.save(newInventory);
	}

	/**
	 * Get inventory by id
	 * @param id
	 * @return
	 */
	@GetMapping("/inventories/{id}")
	Inventory getInventoryById(@PathVariable Long id) {
		return repository.findById(id).get();
	}


/**
 * Delete inventory
 * @param id
 */
	@DeleteMapping("/inventories/{id}")
	void deleteInventory(@PathVariable Long id) {
		repository.deleteById(id);
	}

	
}
