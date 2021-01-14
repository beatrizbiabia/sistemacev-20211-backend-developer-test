package com.survivorapi.resource;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.survivorapi.ZssnapiApplication;
import com.survivorapi.dto.ExchangeDTO;
import com.survivorapi.entity.Item;
import com.survivorapi.entity.Survivor;
import com.survivorapi.exception.ResultException;

/**
 * Test class for the ExchangeResource REST controller.
 * @author Beatriz in 11/01/21
 * @see ItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZssnapiApplication.class)
public class ExchangeResourceIntTest2 {


	@Autowired
	private ExchangeResource exchangeResource;
	
	@Autowired
	private ContaminationFlagResource contaminationFlagResource;

	@Autowired
	private EntityManager em;
	
    private Survivor survivor;
	private Survivor survivor2;
	
	
	@Test
    public void contextLoads() {

    }
    
	  @Before
	    public void setup() {
	        MockitoAnnotations.initMocks(this);
	        final ExchangeResource exchangeResource = new ExchangeResource();
	        MockMvcBuilders.standaloneSetup(exchangeResource).build();
	}
	    
	    @Test
	    @Transactional
	    public void makeExchangeDTOEqualPoints() throws Exception {
	    	

	    	 Set<Item> itemsWanted = new HashSet();
	    	 Set<Item> itemsForPayment=  new HashSet();
	    	
	    	 Item item1 = new Item("medication",2,3);
	    	 Item item11 = new Item("ammunition",1,1);
	    	 itemsWanted.add(item11);
	    	 itemsWanted.add(item1);
	    	 
	    	 Item item2 = new Item("water",4,1);
	    	 Item item22 = new Item("food",3,1);
	    	 itemsForPayment.add(item2);
	    	 itemsForPayment.add(item22);
	    	 
	    	    	      	 
	    	 
	    	Survivor s1 = SurvivorResourceIntTest.createEntity(em);
	    	Survivor s2 = SurvivorResourceIntTest.createEntity(em);
	    	
	    	s1.setItens(itemsWanted);
	    	s2.setItens(itemsForPayment);
	    	s2.setName("Fulano");
	 		em.persist(s1);
	 		em.flush();
	 		
	 		em.persist(s2);
	 		em.flush();
	 		
	 		ExchangeDTO exchange = new ExchangeDTO();
	 		exchange.setUserId(s1.getId());
	 		exchange.setRecipientName(s2.getName());
	 		exchange.setItemsWanted(itemsWanted);
	 		exchange.setItemsForPayment(itemsForPayment);
	 		
	 		exchangeResource.makeExchange(exchange);
	 		
	    	
		}
	    
	    @Test(expected =ResponseStatusException.class)
	    @Transactional
	    public void makeExchangeDTODiferentPoints() throws Exception {
	    	

	    	 Set<Item> itemsWanted = new HashSet();
	    	 Set<Item> itemsForPayment=  new HashSet();
	    	
	    	 Item item1 = new Item("food",3,3);
	    	 Item item11 = new Item("ammunition",1,1);
	    	 itemsWanted.add(item11);
	    	 itemsWanted.add(item1);
	    	 
	    	 Item item2 = new Item("water",4,1);
	    	 Item item22 = new Item("food",3,1);
	    	 itemsForPayment.add(item2);
	    	 itemsForPayment.add(item22);
	    	 
	    	    	      	 
	    	 
	    	Survivor s1 = SurvivorResourceIntTest.createEntity(em);
	    	Survivor s2 = SurvivorResourceIntTest.createEntity(em);
	    	
	    	s1.setItens(itemsWanted);
	    	s2.setItens(itemsForPayment);
	    	s2.setName("Fulano");
	 		em.persist(s1);
	 		em.flush();
	 		
	 		em.persist(s2);
	 		em.flush();
	 		
	 		ExchangeDTO exchange = new ExchangeDTO();
	 		exchange.setUserId(s1.getId());
	 		exchange.setRecipientName(s2.getName());
	 		exchange.setItemsWanted(itemsWanted);
	 		exchange.setItemsForPayment(itemsForPayment);
	 		
	 		exchangeResource.makeExchange(exchange);
	 		
	    	
		}
	    
	    
	    @Test(expected =ResponseStatusException.class)
	    @Transactional
	    public void makeExchangeDTOInfected() throws Exception {
	    	

	    	 Set<Item> itemsWanted = new HashSet();
	    	 Set<Item> itemsForPayment=  new HashSet();
	    	
	    	 Item item1 = new Item("medication",2,3);
	    	 Item item11 = new Item("ammunition",1,1);
	    	 itemsWanted.add(item11);
	    	 itemsWanted.add(item1);
	    	 
	    	 Item item2 = new Item("water",4,1);
	    	 Item item22 = new Item("food",3,1);
	    	 itemsForPayment.add(item2);
	    	 itemsForPayment.add(item22);
	    	 
	    	    	      	 
	    	 
	    	Survivor s1 = SurvivorResourceIntTest.createEntity(em);
	    	Survivor s2 = SurvivorResourceIntTest.createEntity(em);
	    	
	    	
	    	Survivor s3 = SurvivorResourceIntTest.createEntity(em);
	    	Survivor s4 = SurvivorResourceIntTest.createEntity(em);
	    	
	    	s2.setItens(itemsForPayment);
	    	s2.setName("Fulano");
	    	
	    	em.persist(s2);
	 		em.flush();
	 		
	 		em.persist(s3);
	 		em.flush();
	 		
	 		em.persist(s4);
	 		em.flush();
	    	
	    	s1.setItens(itemsWanted);
	    	s1.report(s2);
	    	s1.report(s4);
	    	s1.report(s3);
	    	
	    	em.persist(s1);
	 		em.flush();
	 		
	 		
	 		
	 		ExchangeDTO exchange = new ExchangeDTO();
	 		exchange.setUserId(s1.getId());
	 		exchange.setRecipientName(s2.getName());
	 		exchange.setItemsWanted(itemsWanted);
	 		exchange.setItemsForPayment(itemsForPayment);
	 		
	 		exchangeResource.makeExchange(exchange);
	 		
	    	
		}
}
