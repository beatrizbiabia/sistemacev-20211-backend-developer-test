package com.survivorapi.resource;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.validation.ValidationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.survivorapi.ZssnapiApplication;
import com.survivorapi.entity.Item;
import com.survivorapi.entity.Location;
import com.survivorapi.entity.Survivor;
import com.survivorapi.repository.SurvivorRepository;

/**
 * Test class for the SurvivorResource REST controller.
 * @author Beatriz in 11/01/21
 * @see SurvivorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZssnapiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SurvivorResourceIntTest {

	private static final String DEFAULT_NAME = "AAAAAAAAAA";
	private static final String UPDATED_NAME = "BBBBBBBBBB";
	
	private static final String DEFAULT_GENDER = "Masculino";
	
	private static final Integer DEFAULT_AGE = 1;
	private static final Integer UPDATED_AGE = 2;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private SurvivorRepository survivorRepository;


	@Autowired
	private SurvivorResource survivorService;


//    @Rule
//    public ExpectedException thrown = ExpectedException.none();
//
//    private static Validator validator;
	@Autowired
	private EntityManager em;

	private MockMvc restSurvivorMockMvc;

	private Survivor survivor;

	private Survivor survivor2;
	
	

//    @Test
//    public void contextLoads() {
//
//    }
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SurvivorResource survivorResource = new SurvivorResource();
        this.restSurvivorMockMvc = MockMvcBuilders.standaloneSetup(survivorResource).build();
            
    }

//    @BeforeClass
//    public static void setupValidatorInstance() {
//        validator = Validation.buildDefaultValidatorFactory().getValidator();
//    }


	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Survivor createEntity(EntityManager em) {
		Survivor survivor = new Survivor().name(DEFAULT_NAME).age(DEFAULT_AGE).gender(DEFAULT_GENDER);;
		// Add required entity
		Location lastLocation = LocationResourceIntTest.createEntity(em);
		em.persist(lastLocation);
		em.flush();
		survivor.setLastLocation(lastLocation);
		// Add required entity
		Item inventory1 = ItemResourceIntTest.createEntity(em);
		Item inventory2 = ItemResourceIntTest.createEntity(em);
		
		em.persist(inventory1);
		em.persist(inventory2);
		em.flush();
		Set<Item> itens = new HashSet<Item>();
		itens.add(inventory1);
		itens.add(inventory2);
		survivor.setItens(itens);
		return survivor;
	}

	@Before
	public void initTest() {
		survivor = createEntity(em);
		survivor2 = createEntity(em);
	}

	@Test
	@Transactional
	public void createSurvivor() throws Exception {
		int databaseSizeBeforeCreate = survivorRepository.findAll().size();

		// Create the Survivor
		Survivor survivorDTO = survivor;
		
		survivorRepository.save(survivorDTO);
		
		// Validate the Survivor in the database
		List<Survivor> survivorList = survivorRepository.findAll();
		assertThat(survivorList).hasSize(databaseSizeBeforeCreate + 1);
		Survivor testSurvivor = survivorList.get(survivorList.size() - 1);
		assertThat(testSurvivor.getName()).isEqualTo(DEFAULT_NAME);
		assertThat(testSurvivor.getAge()).isEqualTo(DEFAULT_AGE);
	}

	@Test
	@Transactional
	public void createSurvivorWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = survivorRepository.findAll().size();

			
		// Create the Survivor with an existing ID
		survivor.setId(1L);
		Survivor survivorDTO = survivor;
		survivorRepository.save(survivorDTO);
		List<Survivor> sList = survivorRepository.findAll();
		
		survivor2.setId(sList.iterator().next().getId());
		
		Survivor survivorDTO2 = survivor2;
		survivorRepository.save(survivorDTO2);
		
		List<Survivor> sList2 = survivorRepository.findAll();
		
		  assertThat(sList2).hasSize(sList.size());
		
	}

	 @Test(expected =ValidationException.class)
	@Transactional
	public void checkNameIsRequired() throws Exception {
		int databaseSizeBeforeTest = survivorRepository.findAll().size();
		// set the field null
		survivor.setName(null);

		// Create the Survivor, which fails.
		Survivor survivorDTO = survivor;

		survivorRepository.save(survivorDTO);
	

		
	}

	 @Test(expected =ValidationException.class)
	@Transactional
	public void checkAgeIsRequired() throws Exception {
		int databaseSizeBeforeTest = survivorRepository.findAll().size();
		// set the field null
		survivor.setAge(null);

		// Create the Survivor, which fails.
				Survivor survivorDTO = survivor;

				survivorRepository.save(survivorDTO);
	}

	@Test
	@Transactional
	public void getAllSurvivors() throws Exception {
		// Initialize the database
		survivorRepository.saveAndFlush(survivor);
		
		int databaseSize = survivorRepository.findAll().size();

		List<Survivor> sList = survivorRepository.findAll();
        assertThat(sList).hasSize(databaseSize);
	}

	@Test
	@Transactional
	public void getSurvivor() throws Exception {
		// Initialize the database
		survivorRepository.saveAndFlush(survivor);

		  List<Survivor> sList = survivorRepository.findAll();
          Survivor sList2 = survivorRepository.findById(sList.iterator().next().getId()).get();
        assertThat(sList2).hasFieldOrPropertyWithValue("id",sList.iterator().next().getId());
		
	}



	

	@Test
	@Transactional
	public void getNonExistingSurvivor() throws Exception {
		// Get the survivor
		restSurvivorMockMvc.perform(get("/api/survivors/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateSurvivor() throws Exception {
		// Initialize the database
		survivorRepository.saveAndFlush(survivor);
		int databaseSizeBeforeUpdate = survivorRepository.findAll().size();

		// Update the survivor
		Survivor updatedSurvivor = survivorRepository.findById(survivor.getId()).get();
		// Disconnect from session so that the updates on updatedSurvivor are not
		// directly saved in db
		em.detach(updatedSurvivor);
		updatedSurvivor.name(UPDATED_NAME).age(UPDATED_AGE);
		Survivor survivorDTO =updatedSurvivor;

		survivorRepository.save(survivorDTO);

		// Validate the Survivor in the database
		List<Survivor> survivorList = survivorRepository.findAll();
		assertThat(survivorList).hasSize(databaseSizeBeforeUpdate);
		Survivor testSurvivor = survivorList.get(survivorList.size() - 1);
		assertThat(testSurvivor.getName()).isEqualTo(UPDATED_NAME);
		assertThat(testSurvivor.getAge()).isEqualTo(UPDATED_AGE);
	}

	@Test
	@Transactional
	public void updateNonExistingSurvivor() throws Exception {
		int databaseSizeBeforeUpdate = survivorRepository.findAll().size();

		// Create the Survivor
		Survivor survivorDTO = survivor;

	survivorRepository.save(survivorDTO);

		// Validate the Survivor in the database
		List<Survivor> survivorList = survivorRepository.findAll();
		assertThat(survivorList).hasSize(databaseSizeBeforeUpdate + 1);
	}

	@Test
	@Transactional
	public void deleteSurvivor() throws Exception {
		// Initialize the database
		survivorRepository.saveAndFlush(survivor);
		int databaseSizeBeforeDelete = survivorRepository.findAll().size();

		survivorRepository.delete(survivor);
		// Validate the database is empty
		List<Survivor> survivorList = survivorRepository.findAll();
		assertThat(survivorList).hasSize(databaseSizeBeforeDelete - 1);
	}

	@Test
	@Transactional
	public void equalsVerifier() throws Exception {
		TestUtil.equalsVerifier(Survivor.class);
		Survivor survivor1 = new Survivor();
		survivor1.setId(1L);
		Survivor survivor2 = new Survivor();
		survivor2.setId(survivor1.getId());
		assertThat(survivor1).isEqualTo(survivor2);
		survivor2.setId(2L);
		assertThat(survivor1).isNotEqualTo(survivor2);
		survivor1.setId(null);
		assertThat(survivor1).isNotEqualTo(survivor2);
	}

	@Test
	@Transactional
	public void dtoEqualsVerifier() throws Exception {
		TestUtil.equalsVerifier(Survivor.class);
		Survivor survivorDTO1 = new Survivor();
		survivorDTO1.setId(1L);
		Survivor survivorDTO2 = new Survivor();
		assertThat(survivorDTO1).isNotEqualTo(survivorDTO2);
		survivorDTO2.setId(survivorDTO1.getId());
		assertThat(survivorDTO1).isEqualTo(survivorDTO2);
		survivorDTO2.setId(2L);
		assertThat(survivorDTO1).isNotEqualTo(survivorDTO2);
		survivorDTO1.setId(null);
		assertThat(survivorDTO1).isNotEqualTo(survivorDTO2);
	}


}
