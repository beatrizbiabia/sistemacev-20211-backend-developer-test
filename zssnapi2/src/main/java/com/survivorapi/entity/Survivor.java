/**
 * 
 */
package com.survivorapi.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotNull;

/**
 * @author Beatriz Almeida Sobrinho em 11/01/21
 * 
 * A Survivor
 *
 */
@Entity
@Table(name = "survivor")
	public class Survivor implements Serializable {

		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@NotNull(message = "Nome não pode ser nulo")
		@Column(name = "name", nullable = false)
		private String name;

		@NotNull(message = "Idade não pode ser nulo")
		@Column(name = "age", nullable = false)
		private Integer age;
		
		@NotNull(message = "Genero não pode ser nulo")
		@Column(name = "gender", nullable = false)
		private String gender;


		@OneToOne(optional = false, fetch = FetchType.EAGER,cascade=CascadeType.ALL)
		@NotNull(message = "Localização não pode ser nulo")
		@JoinColumn(unique = true)
		private Location lastLocation;

//		@OneToOne(optional = false,fetch = FetchType.EAGER,cascade=CascadeType.ALL)
//		@NotNull(message = "Inventario não pode ser nulo")
//		@JoinColumn(unique = true)
//		private Inventory inventory;
		
		
		@OneToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
		@NotNull(message = "Inventario não pode ser nulo")
	//	@JoinColumn(unique = true)
		private Set<Item> itens = new HashSet<>();

		@OneToMany(mappedBy = "reported")
		@JsonIgnore
		private Set<ContaminationFlag> reporteds = new HashSet<>();
		
		@Column
	    private Boolean infected;


		
		public Survivor() {
		}

//		public Survivor(String name, String gender, Integer age, Location lastLocation, Inventory inventory) {
//			super();
//			this.name = name;
//			this.gender = gender;
//			this.age = age;
//			this.lastLocation = lastLocation;
//			this.inventory = inventory;
//		}
//		
		public Survivor(String name, String gender, Integer age, Location lastLocation, Set<Item> inventory) {
			super();
			this.name = name;
			this.gender = gender;
			this.age = age;
			this.lastLocation = lastLocation;
			this.itens = inventory;
			this.infected = false;
		}
		
		public Boolean getInfected() {
			return infected;
		}
		
		public void setInfected(Boolean infected) {
			this.infected = infected;
		}
		
		public Set<Item> getItens() {
			return itens;
		}
		
		public Survivor(Set<Item> itens) {
			this.addItens(itens);
		}

		
		private Survivor addItem(Item item) {
			this.itens.add(item);
			//item.setInventory(this);
			return this;
		}

		private Survivor addItens(Set<Item> itens) {
			itens.forEach((Item item) -> this.addItem(item));
			return this;
		}

		private Survivor removeItens(Set<Item> itens) {
			this.itens.removeAll(itens);
			return this;
		}

		public void setItens(Set<Item> items) {
			this.itens = items;
		}


		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public Survivor name(String name) {
			this.name = name;
			return this;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}
		
		public String getGender() {
			return gender;
		}		
		
		public Survivor gender(String gender) {
			this.gender = gender;
			return this;
		}
		public Integer getAge() {
			return age;
		}

		public Survivor age(Integer age) {
			this.age = age;
			return this;
		}

		public void setAge(Integer age) {
			this.age = age;
		}

		public Location getLastLocation() {
			return lastLocation;
		}

		public Survivor lastLocation(Location location) {
			this.lastLocation = location;
			return this;
		}

		public void setLastLocation(Location location) {
			this.lastLocation = location;
		}

//		public Inventory getInventory() {
//			return inventory;
//		}
//
//		public Survivor inventory(Inventory inventory) {
//			this.inventory = inventory;
//			return this;
//		}
//
//		public void setInventory(Inventory inventory) {
//			this.inventory = inventory;
//		}

		public Set<ContaminationFlag> getReporteds() {
			if (reporteds == null) {
				this.setReporteds(new HashSet<>());
			}
			return reporteds;
		}

		public Survivor reporteds(Set<ContaminationFlag> contaminationFlags) {
			this.reporteds = contaminationFlags;
			return this;
		}

		public Survivor addReported(ContaminationFlag contaminationFlag) {
			this.reporteds.add(contaminationFlag);

			for (ContaminationFlag contamination : reporteds) {
				if (contamination.getReportedBy().equals(contaminationFlag.getReportedBy())) {
					return this;
				}
			}

			contaminationFlag.setReported(this);
			return this;
		}

		public Survivor removeReported(ContaminationFlag contaminationFlag) {
			this.reporteds.remove(contaminationFlag);
			contaminationFlag.setReported(null);
			return this;
		}

		public void setReporteds(Set<ContaminationFlag> contaminationFlags) {
			this.reporteds = contaminationFlags;
		}

		public void report(Survivor survivorReporter) {

			ContaminationFlag contaminationFlag = new ContaminationFlag(this, survivorReporter);
			this.addReported(contaminationFlag);
		}

	
		public Boolean isContamined() {
			if (getReporteds().size() > 2)
			
			infected = true;
			else
				infected = false;
			return infected;
		}
		
		public static Boolean trade(Set<Item> leftItens, Set<Item> rightItems) {
			if (!Item.isEqualPoints(leftItens, rightItems)) {
				// Quantidade de pontos não é igual... talvez seja melhor lançar uma exception
				return false;
			}
			
			Set<Item> leftCloned = Item.clone(leftItens);
			Set<Item> rightCloned = Item.clone(rightItems);
			

		//	Item leftInventory = getInventory(leftItens);
		//	Item rightInventory = getInventory(rightItems);
			
			leftItens.removeAll(leftItens);
			rightItems.removeAll(rightItems);

			//leftInventory.removeItens(leftItens);
			//rightInventory.removeItens(rightItems);

			rightItems.addAll(leftCloned);
			leftItens.addAll(rightCloned);

			return true;
		}
		
		private static Item getInventory(Set<Item> itens) {
			// Considero que todos os itens sãodo mesmo Inventory
			return itens.iterator().next();
		}


		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			Survivor survivor = (Survivor) o;
			if (survivor.getId() == null || getId() == null) {
				return false;
			}
			return Objects.equals(getId(), survivor.getId());
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(getId());
		}

		@Override
		public String toString() {
			return "Survivor{" + "id=" + getId() + ", name='" + getName() + "'" + ", age=" + getAge() + "'" + ", infected=" + isContamined() + "}";
		}
}
