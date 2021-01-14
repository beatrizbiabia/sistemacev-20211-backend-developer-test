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

import com.survivorapi.entity.Item;
import com.survivorapi.entity.Location;
import com.survivorapi.entity.Survivor;
import com.survivorapi.repository.ItemRepository;
import com.survivorapi.repository.LocationRepository;
import com.survivorapi.repository.SurvivorRepository;

/**
 * REST controller for managing Survivor.
 * Created by Beatriz on 11/01/21.
 */
@RestController
@RequestMapping("/survivor-management")
public class ItemResource {

	private static final String ENTITY_NAME = "item";

	@Autowired
	private ItemRepository repository;

	public ItemRepository getRepository() {
		return repository;
	}

	/**
	 * List All Itens
	 * @return
	 */
	@GetMapping(value = "/itens")
	public List<Item> getAllItens() {
		return repository.findAll();
	}

	/**
	 * create a new item
	 * @param newItem
	 * @return
	 */
	@PostMapping("/itens")
	Item createOrSaveItem(@RequestBody Item newItem) {
		return repository.save(newItem);
	}

	/**
	 * Get item by id
	 * @param id
	 * @return
	 */
	@GetMapping("/itens/{id}")
	Item getItemById(@PathVariable Long id) {
		return repository.findById(id).get();
	}
/**
 * update item
 * @param newItem
 * @param id
 * @return
 */
	@PutMapping("/itens/{id}")
	Item updateItem(@RequestBody Item newItem, @PathVariable Long id) {

		return repository.findById(id).map(item -> {
			item.setName(newItem.getName());
			item.setPoint(newItem.getPoint());
			return repository.save(item);
		}).orElseGet(() -> {
			newItem.setId(id);
			return repository.save(newItem);
		});
	}
/**
 * delete item
 * @param id
 */
	@DeleteMapping("/itens/{id}")
	void deleteItem(@PathVariable Long id) {
		repository.deleteById(id);
	}

	
}
