package com.dogapp.web.rest;

import com.dogapp.DogappApp;

import com.dogapp.domain.Dog;
import com.dogapp.repository.DogRepository;
import com.dogapp.service.DogService;
import com.dogapp.service.dto.DogDTO;
import com.dogapp.service.mapper.DogMapper;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DogResource REST controller.
 *
 * @see DogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DogappApp.class)
public class DogResourceIntTest {

    private static final String DEFAULT_DOG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOG_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_VOTES = 1L;
    private static final Long UPDATED_VOTES = 2L;

    private static final byte[] DEFAULT_DOG_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOG_PICTURE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_DOG_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOG_PICTURE_CONTENT_TYPE = "image/png";

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private DogMapper dogMapper;

    @Autowired
    private DogService dogService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDogMockMvc;

    private Dog dog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DogResource dogResource = new DogResource(dogService);
        this.restDogMockMvc = MockMvcBuilders.standaloneSetup(dogResource)
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
    public static Dog createEntity(EntityManager em) {
        Dog dog = new Dog()
            .dogName(DEFAULT_DOG_NAME)
            .votes(DEFAULT_VOTES)
            .dogPicture(DEFAULT_DOG_PICTURE)
            .dogPictureContentType(DEFAULT_DOG_PICTURE_CONTENT_TYPE);
        return dog;
    }

    @Before
    public void initTest() {
        dog = createEntity(em);
    }

    @Test
    @Transactional
    public void createDog() throws Exception {
        int databaseSizeBeforeCreate = dogRepository.findAll().size();

        // Create the Dog
        DogDTO dogDTO = dogMapper.dogToDogDTO(dog);
        restDogMockMvc.perform(post("/api/dogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dogDTO)))
            .andExpect(status().isCreated());

        // Validate the Dog in the database
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeCreate + 1);
        Dog testDog = dogList.get(dogList.size() - 1);
        assertThat(testDog.getDogName()).isEqualTo(DEFAULT_DOG_NAME);
        assertThat(testDog.getVotes()).isEqualTo(DEFAULT_VOTES);
        assertThat(testDog.getDogPicture()).isEqualTo(DEFAULT_DOG_PICTURE);
        assertThat(testDog.getDogPictureContentType()).isEqualTo(DEFAULT_DOG_PICTURE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createDogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dogRepository.findAll().size();

        // Create the Dog with an existing ID
        dog.setId(1L);
        DogDTO dogDTO = dogMapper.dogToDogDTO(dog);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDogMockMvc.perform(post("/api/dogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDogs() throws Exception {
        // Initialize the database
        dogRepository.saveAndFlush(dog);

        // Get all the dogList
        restDogMockMvc.perform(get("/api/dogs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dog.getId().intValue())))
            .andExpect(jsonPath("$.[*].dogName").value(hasItem(DEFAULT_DOG_NAME.toString())))
            .andExpect(jsonPath("$.[*].votes").value(hasItem(DEFAULT_VOTES.intValue())))
            .andExpect(jsonPath("$.[*].dogPictureContentType").value(hasItem(DEFAULT_DOG_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dogPicture").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOG_PICTURE))));
    }

    @Test
    @Transactional
    public void getDog() throws Exception {
        // Initialize the database
        dogRepository.saveAndFlush(dog);

        // Get the dog
        restDogMockMvc.perform(get("/api/dogs/{id}", dog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dog.getId().intValue()))
            .andExpect(jsonPath("$.dogName").value(DEFAULT_DOG_NAME.toString()))
            .andExpect(jsonPath("$.votes").value(DEFAULT_VOTES.intValue()))
            .andExpect(jsonPath("$.dogPictureContentType").value(DEFAULT_DOG_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.dogPicture").value(Base64Utils.encodeToString(DEFAULT_DOG_PICTURE)));
    }

    @Test
    @Transactional
    public void getNonExistingDog() throws Exception {
        // Get the dog
        restDogMockMvc.perform(get("/api/dogs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDog() throws Exception {
        // Initialize the database
        dogRepository.saveAndFlush(dog);
        int databaseSizeBeforeUpdate = dogRepository.findAll().size();

        // Update the dog
        Dog updatedDog = dogRepository.findOne(dog.getId());
        updatedDog
            .dogName(UPDATED_DOG_NAME)
            .votes(UPDATED_VOTES)
            .dogPicture(UPDATED_DOG_PICTURE)
            .dogPictureContentType(UPDATED_DOG_PICTURE_CONTENT_TYPE);
        DogDTO dogDTO = dogMapper.dogToDogDTO(updatedDog);

        restDogMockMvc.perform(put("/api/dogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dogDTO)))
            .andExpect(status().isOk());

        // Validate the Dog in the database
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeUpdate);
        Dog testDog = dogList.get(dogList.size() - 1);
        assertThat(testDog.getDogName()).isEqualTo(UPDATED_DOG_NAME);
        assertThat(testDog.getVotes()).isEqualTo(UPDATED_VOTES);
        assertThat(testDog.getDogPicture()).isEqualTo(UPDATED_DOG_PICTURE);
        assertThat(testDog.getDogPictureContentType()).isEqualTo(UPDATED_DOG_PICTURE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingDog() throws Exception {
        int databaseSizeBeforeUpdate = dogRepository.findAll().size();

        // Create the Dog
        DogDTO dogDTO = dogMapper.dogToDogDTO(dog);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDogMockMvc.perform(put("/api/dogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dogDTO)))
            .andExpect(status().isCreated());

        // Validate the Dog in the database
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDog() throws Exception {
        // Initialize the database
        dogRepository.saveAndFlush(dog);
        int databaseSizeBeforeDelete = dogRepository.findAll().size();

        // Get the dog
        restDogMockMvc.perform(delete("/api/dogs/{id}", dog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dog.class);
    }
}
