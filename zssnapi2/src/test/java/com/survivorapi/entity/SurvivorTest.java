package com.survivorapi.entity;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 
 * Class Test for Survivor
 * 
 * @author Beatriz in 11/01/21
 *
 */
@SpringBootTest
public class SurvivorTest {
	
//	SurvivorResource survivorResource = new SurvivorResource();

	Survivor survivorClayton;
	Survivor survivorDaniela;
	Survivor survivorCatherine;
	Survivor survivorSophia;

	@Before
	public void beforeTest() {
		makeSurvivorClayton();
		makeSurvivorDaniela();
		makeSurvivorCatherine();
		makeSurvivorSophia();
	}

	@Test
	public void isContaminedTrueTest() {
		survivorDaniela.report(survivorCatherine);
		survivorDaniela.report(survivorSophia);
		survivorDaniela.report(survivorClayton);
		Assert.assertTrue("Deveria ser true, estar contaminado", survivorDaniela.isContamined());
	}
	
	@Test
	public void isContaminedFalseDontCountWhenRepeatReportTest() {
		survivorDaniela.report(survivorCatherine);
		survivorDaniela.report(survivorSophia);
		survivorDaniela.report(survivorCatherine);
		survivorDaniela.report(survivorCatherine);
		survivorDaniela.report(survivorCatherine);
		Assert.assertTrue("Deveria ser false, Não está contaminado", survivorDaniela.isContamined());
	}
	
	@Test
	public void isContaminedFalseTest() {
		survivorDaniela.report(survivorCatherine);
		survivorDaniela.report(survivorSophia);
		Assert.assertFalse("Deveria ser false, não está contaminado", survivorDaniela.isContamined());
	}

	private Survivor makeSurvivorClayton() {
		Location lastLocation = new Location("-23.3785587", "-51.9338098,18");
		Set<Item> inventory = new HashSet<Item>();
		Item item1 = new Item("water",4,2);
		Item item2 = new Item("food",3,1);
		inventory.add(item2);
		inventory.add(item1);
		this.survivorClayton = new Survivor("Clayton","Masculino", 38, lastLocation, inventory);
		this.survivorClayton.setId(1L);
		return survivorClayton;
	}

	
	private Survivor makeSurvivorDaniela() {
		Location lastLocation = new Location("-23.3785587", "-51.9338098,18");
		Set<Item> inventory = new HashSet<Item>();
		Item item1 = new Item("water",4,2);
		Item item2 = new Item("medication",2,1);
		inventory.add(item2);
		inventory.add(item1);
		this.survivorDaniela = new Survivor("Daniela", "Feminino",38, lastLocation, inventory);
		
	//	survivorResource.getRepository().save(survivorDaniela);
		
		//Assert.assertNotNull(survivorDaniela);
		this.survivorDaniela.setId(2L);
		return survivorDaniela;
	}

	private Survivor makeSurvivorCatherine() {
		Location lastLocation = new Location("-23.3785587", "-51.9338098,18");
		Set<Item> inventory = new HashSet<Item>();
		Item item1 = new Item("water",4,2);
		Item item2 = new Item("food",3,1);
		inventory.add(item2);
		inventory.add(item1);
		this.survivorCatherine = new Survivor("Catherine","Feminino", 38, lastLocation, inventory);
		this.survivorCatherine.setId(3L);
		return survivorCatherine;
	}

	private Survivor makeSurvivorSophia() {
		Location lastLocation = new Location("-23.3785587", "-51.9338098,18");
		Set<Item> inventory = new HashSet<Item>();
		Item item1 = new Item("water",4,2);
		Item item2 = new Item("food",3,1);
		inventory.add(item2);
		inventory.add(item1);
		this.survivorSophia = new Survivor("Sophia","Feminino", 20, lastLocation, inventory);
		this.survivorSophia.setId(4L);
		return survivorSophia;
	}

	@Test
	public void contextLoads() {
		
	}
}
