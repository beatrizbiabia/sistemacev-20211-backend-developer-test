package com.survivorapi.repository;


import org.springframework.stereotype.Repository;

import com.survivorapi.entity.Inventory;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Inventory entity.
 * Created by Beatriz on 11/01/21.
 */
@SuppressWarnings("unused")
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long>, JpaSpecificationExecutor<Inventory> {

}
