package com.dogapp.web.rest;

import com.dogapp.DogappApp;

import com.dogapp.domain.UserDog;
import com.dogapp.repository.UserDogRepository;
import com.dogapp.service.UserDogService;
import com.dogapp.service.dto.UserDogDTO;
import com.dogapp.service.mapper.UserDogMapper;
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
 * Test class for the UserDogResource REST controller.
 *
 * @see UserDogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DogappApp.class)
public class UserDogResourceIntTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    @Autowired
    private UserDogRepository userDogRepository;

    @Autowired
    private UserDogMapper userDogMapper;

    @Autowired
    private UserDogService userDogService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserDogMockMvc;

    private UserDog userDog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserDogResource userDogResource = new UserDogResource(userDogService);
        this.restUserDogMockMvc = MockMvcBuilders.standaloneSetup(userDogResource)
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
    public static UserDog createEntity(EntityManager em) {
        UserDog userDog = new UserDog()
            .username(DEFAULT_USERNAME);
        return userDog;
    }

    @Before
    public void initTest() {
        userDog = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserDog() throws Exception {
        int databaseSizeBeforeCreate = userDogRepository.findAll().size();

        // Create the UserDog
        UserDogDTO userDogDTO = userDogMapper.userDogToUserDogDTO(userDog);
        restUserDogMockMvc.perform(post("/api/user-dogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDogDTO)))
            .andExpect(status().isCreated());

        // Validate the UserDog in the database
        List<UserDog> userDogList = userDogRepository.findAll();
        assertThat(userDogList).hasSize(databaseSizeBeforeCreate + 1);
        UserDog testUserDog = userDogList.get(userDogList.size() - 1);
        assertThat(testUserDog.getUsername()).isEqualTo(DEFAULT_USERNAME);
    }

    @Test
    @Transactional
    public void createUserDogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userDogRepository.findAll().size();

        // Create the UserDog with an existing ID
        userDog.setId(1L);
        UserDogDTO userDogDTO = userDogMapper.userDogToUserDogDTO(userDog);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserDogMockMvc.perform(post("/api/user-dogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserDog> userDogList = userDogRepository.findAll();
        assertThat(userDogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserDogs() throws Exception {
        // Initialize the database
        userDogRepository.saveAndFlush(userDog);

        // Get all the userDogList
        restUserDogMockMvc.perform(get("/api/user-dogs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userDog.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())));
    }

    @Test
    @Transactional
    public void getUserDog() throws Exception {
        // Initialize the database
        userDogRepository.saveAndFlush(userDog);

        // Get the userDog
        restUserDogMockMvc.perform(get("/api/user-dogs/{id}", userDog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userDog.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserDog() throws Exception {
        // Get the userDog
        restUserDogMockMvc.perform(get("/api/user-dogs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserDog() throws Exception {
        // Initialize the database
        userDogRepository.saveAndFlush(userDog);
        int databaseSizeBeforeUpdate = userDogRepository.findAll().size();

        // Update the userDog
        UserDog updatedUserDog = userDogRepository.findOne(userDog.getId());
        updatedUserDog
            .username(UPDATED_USERNAME);
        UserDogDTO userDogDTO = userDogMapper.userDogToUserDogDTO(updatedUserDog);

        restUserDogMockMvc.perform(put("/api/user-dogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDogDTO)))
            .andExpect(status().isOk());

        // Validate the UserDog in the database
        List<UserDog> userDogList = userDogRepository.findAll();
        assertThat(userDogList).hasSize(databaseSizeBeforeUpdate);
        UserDog testUserDog = userDogList.get(userDogList.size() - 1);
        assertThat(testUserDog.getUsername()).isEqualTo(UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void updateNonExistingUserDog() throws Exception {
        int databaseSizeBeforeUpdate = userDogRepository.findAll().size();

        // Create the UserDog
        UserDogDTO userDogDTO = userDogMapper.userDogToUserDogDTO(userDog);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserDogMockMvc.perform(put("/api/user-dogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDogDTO)))
            .andExpect(status().isCreated());

        // Validate the UserDog in the database
        List<UserDog> userDogList = userDogRepository.findAll();
        assertThat(userDogList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserDog() throws Exception {
        // Initialize the database
        userDogRepository.saveAndFlush(userDog);
        int databaseSizeBeforeDelete = userDogRepository.findAll().size();

        // Get the userDog
        restUserDogMockMvc.perform(delete("/api/user-dogs/{id}", userDog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserDog> userDogList = userDogRepository.findAll();
        assertThat(userDogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserDog.class);
    }
}
