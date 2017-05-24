package com.dogapp.web.rest;

import com.dogapp.DogappApp;

import com.dogapp.domain.DogBreed;
import com.dogapp.repository.DogBreedRepository;
import com.dogapp.service.DogBreedService;
import com.dogapp.service.dto.DogBreedDTO;
import com.dogapp.service.mapper.DogBreedMapper;
import com.dogapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DogBreedResource REST controller.
 *
 * @see DogBreedResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DogappApp.class)
public class DogBreedResourceIntTest {

    private static final String DEFAULT_BREED_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BREED_NAME = "BBBBBBBBBB";

    @Autowired
    private DogBreedRepository dogBreedRepository;

    @Autowired
    private DogBreedMapper dogBreedMapper;

    @Autowired
    private DogBreedService dogBreedService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDogBreedMockMvc;

    private DogBreed dogBreed;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DogBreedResource dogBreedResource = new DogBreedResource(dogBreedService);
        this.restDogBreedMockMvc = MockMvcBuilders.standaloneSetup(dogBreedResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DogBreed createEntity(EntityManager em) {
        DogBreed dogBreed = new DogBreed()
            .breedName(DEFAULT_BREED_NAME);
        return dogBreed;
    }

    @Before
    public void initTest() {
        dogBreed = createEntity(em);
    }

    @Test
    @Transactional
    public void createDogBreed() throws Exception {
        int databaseSizeBeforeCreate = dogBreedRepository.findAll().size();

        // Create the DogBreed
        DogBreedDTO dogBreedDTO = dogBreedMapper.dogBreedToDogBreedDTO(dogBreed);
        restDogBreedMockMvc.perform(post("/api/dog-breeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dogBreedDTO)))
            .andExpect(status().isCreated());

        // Validate the DogBreed in the database
        List<DogBreed> dogBreedList = dogBreedRepository.findAll();
        assertThat(dogBreedList).hasSize(databaseSizeBeforeCreate + 1);
        DogBreed testDogBreed = dogBreedList.get(dogBreedList.size() - 1);
        assertThat(testDogBreed.getBreedName()).isEqualTo(DEFAULT_BREED_NAME);
    }

    @Test
    @Transactional
    public void createDogBreedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dogBreedRepository.findAll().size();

        // Create the DogBreed with an existing ID
        dogBreed.setId(1L);
        DogBreedDTO dogBreedDTO = dogBreedMapper.dogBreedToDogBreedDTO(dogBreed);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDogBreedMockMvc.perform(post("/api/dog-breeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dogBreedDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DogBreed> dogBreedList = dogBreedRepository.findAll();
        assertThat(dogBreedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDogBreeds() throws Exception {
        // Initialize the database
        dogBreedRepository.saveAndFlush(dogBreed);

        // Get all the dogBreedList
        restDogBreedMockMvc.perform(get("/api/dog-breeds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dogBreed.getId().intValue())))
            .andExpect(jsonPath("$.[*].breedName").value(hasItem(DEFAULT_BREED_NAME.toString())));
    }

    @Test
    @Transactional
    public void getDogBreed() throws Exception {
        // Initialize the database
        dogBreedRepository.saveAndFlush(dogBreed);

        // Get the dogBreed
        restDogBreedMockMvc.perform(get("/api/dog-breeds/{id}", dogBreed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dogBreed.getId().intValue()))
            .andExpect(jsonPath("$.breedName").value(DEFAULT_BREED_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDogBreed() throws Exception {
        // Get the dogBreed
        restDogBreedMockMvc.perform(get("/api/dog-breeds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDogBreed() throws Exception {
        // Initialize the database
        dogBreedRepository.saveAndFlush(dogBreed);
        int databaseSizeBeforeUpdate = dogBreedRepository.findAll().size();

        // Update the dogBreed
        DogBreed updatedDogBreed = dogBreedRepository.findOne(dogBreed.getId());
        updatedDogBreed
            .breedName(UPDATED_BREED_NAME);
        DogBreedDTO dogBreedDTO = dogBreedMapper.dogBreedToDogBreedDTO(updatedDogBreed);

        restDogBreedMockMvc.perform(put("/api/dog-breeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dogBreedDTO)))
            .andExpect(status().isOk());

        // Validate the DogBreed in the database
        List<DogBreed> dogBreedList = dogBreedRepository.findAll();
        assertThat(dogBreedList).hasSize(databaseSizeBeforeUpdate);
        DogBreed testDogBreed = dogBreedList.get(dogBreedList.size() - 1);
        assertThat(testDogBreed.getBreedName()).isEqualTo(UPDATED_BREED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDogBreed() throws Exception {
        int databaseSizeBeforeUpdate = dogBreedRepository.findAll().size();

        // Create the DogBreed
        DogBreedDTO dogBreedDTO = dogBreedMapper.dogBreedToDogBreedDTO(dogBreed);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDogBreedMockMvc.perform(put("/api/dog-breeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dogBreedDTO)))
            .andExpect(status().isCreated());

        // Validate the DogBreed in the database
        List<DogBreed> dogBreedList = dogBreedRepository.findAll();
        assertThat(dogBreedList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDogBreed() throws Exception {
        // Initialize the database
        dogBreedRepository.saveAndFlush(dogBreed);
        int databaseSizeBeforeDelete = dogBreedRepository.findAll().size();

        // Get the dogBreed
        restDogBreedMockMvc.perform(delete("/api/dog-breeds/{id}", dogBreed.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DogBreed> dogBreedList = dogBreedRepository.findAll();
        assertThat(dogBreedList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DogBreed.class);
    }
}
