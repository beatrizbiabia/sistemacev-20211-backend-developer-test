package com.survivorapi.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Beatriz Almeida Sobrinho em 11/01/21
 * 
 * A Item.
 */
@Entity
@Table(name = "item")
public class Item implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Nome do Item não pode ser nulo")
	@Column(name = "name", nullable = false)
	private String name;

	@NotNull
	//@Min(value = 1L)
	@Column(name = "point", nullable = false)
	private Integer point;
	
	
	@Column(name = "quantity", nullable = false)
	private Integer quantity;
	
	@Column(name = "iten", nullable = true)
	private EPoint item;
	
//	@ManyToOne
	//private Survivor survivorOwner;

//	 
//	 @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
//     @JoinColumn(name="inventory_id")
//   	private Inventory inventory;



	public Item() {
	}

//	public Item(String name, Long point, Inventory inventory) {
//		this.name = name;
//		this.point = point;
//		this.inventory = inventory;
//	}

	public Item(String name, Integer point, Integer quantity ) {
		this.name = name;
		this.point = point;
		this.quantity = quantity;
	//	this.survivorOwner = survivorOwner;
	}
	
	public Item(EPoint item, Integer quantity ) {
		this.item = item;
	//	this.point = point;
		this.quantity = quantity;
	//	this.survivorOwner = survivorOwner;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public EPoint getItem() {
		return item;
	}
	
	public void setItem(EPoint item) {
		this.item = item;
	}
	
//	public Survivor getSurvivorOwner() {
//		return survivorOwner;
//	}
//	
//	public void setSurvivorOwner(Survivor survivorOwner) {
//		this.survivorOwner = survivorOwner;
//	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Item quantity(Integer quantity) {
		this.quantity = quantity;
		return this;
	}

	
	public String getName() {
		return name;
	}

	public Item name(String name) {
		this.name = name;
		return this;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPoint() {
		return point;
	}

	public Item point(Integer point) {
		this.point = point;
		return this;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

//	public Inventory getInventory() {
//		return inventory;
//	}
//
//	public Item inventory(Inventory inventory) {
//		this.inventory = inventory;
//		return this;
//	}
//
//	public void setInventory(Inventory inventory) {
//		this.inventory = inventory;
//	}


	public static Long sumPoint(Set<Item> itens) {
		if (itens == null || itens.size() == 0) {
			return 0L;
		}

		return itens.stream().mapToLong(item -> item.getPoint()).sum();
	}

	public static Boolean isEqualPoints(Set<Item> itensLeft, Set<Item> rightItems) {
		Long rightPoints = sumPoint(rightItems);
		Long leftPoins = sumPoint(itensLeft);
		return leftPoins.equals(rightPoints);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Item item = (Item) o;
		if (item.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), item.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "Item{" + "id=" + getId() + ", name='" + getName() + "'" + ", point=" + getPoint() + "'" + ", quantity=" + getQuantity() + "}";
	}

	@Override
	public Item clone() {
		Item clone = new Item();
		clone.setId(id);
		clone.setPoint(point);
		clone.setName(name);
		return clone;
	}

	public static Set<Item> clone(Set<Item> itens) {
		return itens.stream().map((Item item) -> item.clone()).collect(Collectors.toSet());
	}


}
