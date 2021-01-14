package com.survivorapi.resource;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.survivorapi.ZssnapiApplication;
import com.survivorapi.entity.Item;
import com.survivorapi.repository.ItemRepository;

/**
 * Test class for the ItemResource REST controller.
 * @author Beatriz in 11/01/21
 * @see ItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZssnapiApplication.class)
public class ItemResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_POINT = 1;
    private static final Integer UPDATED_POINT = 2;

    private static final Integer DEFAULT_QUANTITY = 2;
    private static final Integer UPDATED_QUANTITY = 2;
    
    @Autowired
    private ItemRepository itemRepository;

  

    
    @Autowired
    private EntityManager em;

    private MockMvc restItemMockMvc;

    private Item item;
    private Item item2;
    
//    @Test
//    public void contextLoads() {
//
//    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemResource itemResource = new ItemResource();
        this.restItemMockMvc = MockMvcBuilders.standaloneSetup(itemResource).build();
            
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Item createEntity(EntityManager em) {
        Item item = new Item()
            .name(DEFAULT_NAME)
            .point(DEFAULT_POINT)
            .quantity(DEFAULT_QUANTITY);
        // Add required entity
   //     Item inventory = InventoryResourceIntTest.createEntity(em);
        em.persist(item);
        em.flush();
     //   item.setInventory(inventory);
        return item;
    }

    @Before
    public void initTest() {
        item = createEntity(em);
        item2 = createEntity(em);
    }

    @Test
    @Transactional
    public void createItem() throws Exception {
        int databaseSizeBeforeCreate = itemRepository.findAll().size();

        // Create the Item
        Item itemDTO = item2;
     
        itemRepository.save(itemDTO);
        
        // Validate the Item in the database
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeCreate );
        
        Item testItem = itemList.get(itemList.size() - 1);
        assertThat(testItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testItem.getPoint()).isEqualTo(DEFAULT_POINT);
    }

    @Test
    @Transactional
    public void createItemWithExistingId() throws Exception {
    	
    	
    	List<Item> sList = itemRepository.findAll();
    	
    	  Item item3 = new Item()
    	            .name(DEFAULT_NAME)
    	            .point(DEFAULT_POINT)
    	            .quantity(DEFAULT_QUANTITY);

    	item3.setId(sList.iterator().next().getId());
    	
    	Item itemDTO2 = item3;
		itemRepository.save(itemDTO2);
		
		
		
		List<Item> sList2 = itemRepository.findAll();
		
		  assertThat(sList2).hasSize(sList.size());
             
       
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
      
        // set the field null
        item.setName(null);

       itemRepository.save(item);
 

    }

    @Test
    @Transactional
    public void checkPointIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemRepository.findAll().size();
        // set the field null
        item.setPoint(null);

        itemRepository.save(item);
    }

    @Test
    @Transactional
    public void getAllItems() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);
        int databaseSize = itemRepository.findAll().size();
        
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSize);
    }

    @Test
    @Transactional
    public void getItem() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);
        
        List<Item> itemList = itemRepository.findAll();
          Item itemList2 = itemRepository.findById(itemList.iterator().next().getId()).get();
        assertThat(itemList2).hasFieldOrPropertyWithValue("id",itemList.iterator().next().getId());

    }




    @Test
    @Transactional
    public void getNonExistingItem() throws Exception {
        // Get the item
        restItemMockMvc.perform(get("/api/items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItem() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);
        int databaseSizeBeforeUpdate = itemRepository.findAll().size();

        // Update the item
        Item updatedItem = itemRepository.findById(item.getId()).get();
        // Disconnect from session so that the updates on updatedItem are not directly saved in db
        em.detach(updatedItem);
        updatedItem
            .name(UPDATED_NAME)
            .point(UPDATED_POINT);
      
        Item itemDTO = updatedItem;

        itemRepository.save(itemDTO);
        
       

        // Validate the Item in the database
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeUpdate);
    //    Item testItem = itemList.get(itemList.size() - 1);
        Item testItem = itemRepository.findById(itemDTO.getId()).get();
        
        assertThat(testItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testItem.getPoint()).isEqualTo(UPDATED_POINT);
    }

    @Test
    @Transactional
    public void updateNonExistingItem() throws Exception {
        int databaseSizeBeforeUpdate = itemRepository.findAll().size();

        // Create the Item
      
         itemRepository.save(item);
        // Validate the Item in the database
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeUpdate );
    }

    @Test
    @Transactional
    public void deleteItem() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);
        int databaseSizeBeforeDelete = itemRepository.findAll().size();

      itemRepository.delete(item);

        // Validate the database is empty
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Item.class);
        Item item1 = new Item();
        item1.setId(1L);
        Item item2 = new Item();
        item2.setId(item1.getId());
        assertThat(item1).isEqualTo(item2);
        item2.setId(2L);
        assertThat(item1).isNotEqualTo(item2);
        item1.setId(null);
        assertThat(item1).isNotEqualTo(item2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Item.class);
        Item itemDTO1 = new Item();
        itemDTO1.setId(1L);
        Item itemDTO2 = new Item();
        assertThat(itemDTO1).isNotEqualTo(itemDTO2);
        itemDTO2.setId(itemDTO1.getId());
        assertThat(itemDTO1).isEqualTo(itemDTO2);
        itemDTO2.setId(2L);
        assertThat(itemDTO1).isNotEqualTo(itemDTO2);
        itemDTO1.setId(null);
        assertThat(itemDTO1).isNotEqualTo(itemDTO2);
    }

//    @Test
//    @Transactional
//    public void testEntityFromId() {
//        assertThat(itemMapper.fromId(42L).getId()).isEqualTo(42);
//        assertThat(itemMapper.fromId(null)).isNull();
//    }
}
