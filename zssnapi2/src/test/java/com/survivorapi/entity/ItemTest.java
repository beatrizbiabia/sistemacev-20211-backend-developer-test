package com.survivorapi.entity;

import java.util.HashSet;
import java.util.Set;

import org.assertj.core.util.Sets;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * Class Test for Item
 * 
 * @author Beatriz in 11/01/21
 *
 */
@SpringBootTest
public class ItemTest {

	private Set<Item> leftItens;
	private Set<Item> rightItens;

	@Before
	public void before() throws Exception {
		initializeLeftItens();
		initializeRightItens();
	}

	@Test
	public void sumLeftItenNullTest() {
		Long sumLeftPoints = Item.sumPoint(null);
		Assert.assertEquals(new Long(0), sumLeftPoints);
	}

	@Test
	public void sumLeftZeroItemTest() {
		Long sumLeftPoints = Item.sumPoint(Sets.newLinkedHashSet());
		Assert.assertEquals(new Long(0), sumLeftPoints);
	}

	@Test
	public void sumLeftItensTest() {
		Long sumLeftPoints = Item.sumPoint(this.leftItens);
		Assert.assertEquals(new Long(7), sumLeftPoints);
	}

	@Test
	public void isEqualPointFalseTest() {
		Boolean equalPoints = Item.isEqualPoints(leftItens, rightItens);
		org.junit.Assert.assertFalse(equalPoints);
	}
	
	@Test
	public void isEqualPointTrueTest() {
		Boolean equalPoints = Item.isEqualPoints(leftItens, leftItens);
		org.junit.Assert.assertTrue(equalPoints);
	}

	private void initializeLeftItens() {
		this.leftItens = new HashSet<>();
		leftItens.add(new Item("food", 3, null));
		leftItens.add(new Item("water", 4, null));
	}

	private void initializeRightItens() {
		this.rightItens = new HashSet<>();
		rightItens.add(new Item("medication", 2, null));
		rightItens.add(new Item("ammunition",1, null));
	}
	
	@Test
	public void contextLoads() {
		
	}

}
