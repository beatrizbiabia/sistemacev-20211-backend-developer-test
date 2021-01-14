package com.survivorapi.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityManager;
import javax.validation.ValidationException;

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
import com.survivorapi.entity.Location;
import com.survivorapi.repository.LocationRepository;

/**
 * Test class for the LocationResource REST controller.
 * @author Beatriz in 11/01/21
 * @see LocationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZssnapiApplication.class)
public class LocationResourceIntTest {

    private static final String DEFAULT_LONGITUDE = "123";
    private static final String UPDATED_LONGITUDE = "443";

    private static final String DEFAULT_LATITUDE = "43";
    private static final String UPDATED_LATITUDE = "141";

    @Autowired
    private LocationRepository locationRepository;


    @Autowired
    private EntityManager em;

    private MockMvc restLocationMockMvc;

    private Location location;
    private Location location2;
    
//    @Autowired
//	private TestRestTemplate restTemplate;
    
//    @LocalServerPort
//    private int port;



    @Test
    public void contextLoads() {

    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocationResource locationResource = new LocationResource();
        this.restLocationMockMvc = MockMvcBuilders.standaloneSetup(locationResource).build();
            
    }
    
//    @BeforeClass
//    public static void setupValidatorInstance() {
//        validator = Validation.buildDefaultValidatorFactory().getValidator();
//    }


    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Location createEntity(EntityManager em) {
        Location location = new Location()
            .longitude(DEFAULT_LONGITUDE)
            .latitude(DEFAULT_LATITUDE);
        return location;
    }

    @Before
    public void initTest() {
        location = createEntity(em);
        location2 = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocation() throws Exception {
        int databaseSizeBeforeCreate = locationRepository.findAll().size();

        // Create the Location
        Location locationDTO =location;
//        restLocationMockMvc.perform(post("/survivor-management/locations")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(locationDTO)))
//            .andExpect(status().isCreated());

        locationRepository.save(locationDTO);
        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeCreate + 1);
        Location testLocation = locationList.get(locationList.size() - 1);
        assertThat(testLocation.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testLocation.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
    }

    @Test
    @Transactional
    public void createLocationWithExistingId() throws Exception {
        //int databaseSizeBeforeCreate = locationRepository.findAll().size();

        // Create the Location with an existing ID
        location.setId(1L);
        Location locationDTO = location;



        locationRepository.save(locationDTO);
        List<Location> locationList = locationRepository.findAll();
        
        location2.setId(locationList.iterator().next().getId());
        Location locationDTO2 = location2;
        
        locationRepository.save(location2);
        // Validate the Location in the database
        List<Location> locationList2 = locationRepository.findAll();
        assertThat(locationList2).hasSize(locationList.size());
    }

    @Test(expected =ValidationException.class)
    @Transactional
    public void checkLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationRepository.findAll().size();
        // set the field null
        location.setLongitude(null);

        // Create the Location, which fails.
        Location locationDTO = location;

        locationRepository.save(locationDTO);
      
    }

    @Test(expected =ValidationException.class)
    @Transactional
    public void checkLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationRepository.findAll().size();
        // set the field null
        location.setLatitude(null);

        // Create the Location, which fails.
        Location locationDTO = location;

				   locationRepository.save(locationDTO);
		        

        
        
      //  List<Location> locationList = locationRepository.findAll();
      //  assertThat(locationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLocations() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);
        
        int databaseSize = locationRepository.findAll().size();
        
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSize);
        // Get all the locationList
//        restLocationMockMvc.perform(get("/api/locations?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(location.getId().intValue())))
//            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.toString())))
//            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.toString())));
    }

    @Test
    @Transactional
    public void getLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);
        
        List<Location> locationList = locationRepository.findAll();
          Location locationList2 = locationRepository.findById(locationList.iterator().next().getId()).get();
        assertThat(locationList2).hasFieldOrPropertyWithValue("id",locationList.iterator().next().getId());

    }


    @Test
    @Transactional
    public void getNonExistingLocation() throws Exception {
        // Get the location
        restLocationMockMvc.perform(get("/api/locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);
        int databaseSizeBeforeUpdate = locationRepository.findAll().size();

        // Update the location
        Location updatedLocation = locationRepository.findById(location.getId()).get();
        // Disconnect from session so that the updates on updatedLocation are not directly saved in db
        em.detach(updatedLocation);
        updatedLocation
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE);
        Location locationDTO = updatedLocation;



        locationRepository.save(updatedLocation);
        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
        Location testLocation = locationList.get(locationList.size() - 1);
        assertThat(testLocation.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testLocation.getLatitude()).isEqualTo(UPDATED_LATITUDE);
    }



    @Test
    @Transactional
    public void deleteLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);
        int databaseSizeBeforeDelete = locationRepository.findAll().size();

//        // Get the location
//        restLocationMockMvc.perform(delete("/api/locations/{id}", location.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isOk());
locationRepository.delete(location);
        // Validate the database is empty
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Location.class);
        Location location1 = new Location();
        location1.setId(1L);
        Location location2 = new Location();
        location2.setId(location1.getId());
        assertThat(location1).isEqualTo(location2);
        location2.setId(2L);
        assertThat(location1).isNotEqualTo(location2);
        location1.setId(null);
        assertThat(location1).isNotEqualTo(location2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Location.class);
        Location locationDTO1 = new Location();
        locationDTO1.setId(1L);
        Location locationDTO2 = new Location();
        assertThat(locationDTO1).isNotEqualTo(locationDTO2);
        locationDTO2.setId(locationDTO1.getId());
        assertThat(locationDTO1).isEqualTo(locationDTO2);
        locationDTO2.setId(2L);
        assertThat(locationDTO1).isNotEqualTo(locationDTO2);
        locationDTO1.setId(null);
        assertThat(locationDTO1).isNotEqualTo(locationDTO2);
    }

//    @Test
//    @Transactional
//    public void testEntityFromId() {
//        assertThat(locationMapper.fromId(42L).getId()).isEqualTo(42);
//        assertThat(locationMapper.fromId(null)).isNull();
//    }
}
