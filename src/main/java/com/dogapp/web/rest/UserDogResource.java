package com.dogapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dogapp.service.UserDogService;
import com.dogapp.web.rest.util.HeaderUtil;
import com.dogapp.service.dto.UserDogDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing UserDog.
 */
@RestController
@RequestMapping("/api")
public class UserDogResource {

    private final Logger log = LoggerFactory.getLogger(UserDogResource.class);

    private static final String ENTITY_NAME = "userDog";
        
    private final UserDogService userDogService;

    public UserDogResource(UserDogService userDogService) {
        this.userDogService = userDogService;
    }

    /**
     * POST  /user-dogs : Create a new userDog.
     *
     * @param userDogDTO the userDogDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userDogDTO, or with status 400 (Bad Request) if the userDog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-dogs")
    @Timed
    public ResponseEntity<UserDogDTO> createUserDog(@RequestBody UserDogDTO userDogDTO) throws URISyntaxException {
        log.debug("REST request to save UserDog : {}", userDogDTO);
        if (userDogDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userDog cannot already have an ID")).body(null);
        }
        UserDogDTO result = userDogService.save(userDogDTO);
        return ResponseEntity.created(new URI("/api/user-dogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-dogs : Updates an existing userDog.
     *
     * @param userDogDTO the userDogDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userDogDTO,
     * or with status 400 (Bad Request) if the userDogDTO is not valid,
     * or with status 500 (Internal Server Error) if the userDogDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-dogs")
    @Timed
    public ResponseEntity<UserDogDTO> updateUserDog(@RequestBody UserDogDTO userDogDTO) throws URISyntaxException {
        log.debug("REST request to update UserDog : {}", userDogDTO);
        if (userDogDTO.getId() == null) {
            return createUserDog(userDogDTO);
        }
        UserDogDTO result = userDogService.save(userDogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userDogDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-dogs : get all the userDogs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userDogs in body
     */
    @GetMapping("/user-dogs")
    @Timed
    public List<UserDogDTO> getAllUserDogs() {
        log.debug("REST request to get all UserDogs");
        return userDogService.findAll();
    }

    /**
     * GET  /user-dogs/:id : get the "id" userDog.
     *
     * @param id the id of the userDogDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userDogDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-dogs/{id}")
    @Timed
    public ResponseEntity<UserDogDTO> getUserDog(@PathVariable Long id) {
        log.debug("REST request to get UserDog : {}", id);
        UserDogDTO userDogDTO = userDogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userDogDTO));
    }

    /**
     * DELETE  /user-dogs/:id : delete the "id" userDog.
     *
     * @param id the id of the userDogDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-dogs/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserDog(@PathVariable Long id) {
        log.debug("REST request to delete UserDog : {}", id);
        userDogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
