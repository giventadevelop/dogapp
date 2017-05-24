package com.dogapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dogapp.service.DogBreedService;
import com.dogapp.web.rest.util.HeaderUtil;
import com.dogapp.service.dto.DogBreedDTO;
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
 * REST controller for managing DogBreed.
 */
@RestController
@RequestMapping("/api")
public class DogBreedResource {

    private final Logger log = LoggerFactory.getLogger(DogBreedResource.class);

    private static final String ENTITY_NAME = "dogBreed";
        
    private final DogBreedService dogBreedService;

    public DogBreedResource(DogBreedService dogBreedService) {
        this.dogBreedService = dogBreedService;
    }

    /**
     * POST  /dog-breeds : Create a new dogBreed.
     *
     * @param dogBreedDTO the dogBreedDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dogBreedDTO, or with status 400 (Bad Request) if the dogBreed has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dog-breeds")
    @Timed
    public ResponseEntity<DogBreedDTO> createDogBreed(@RequestBody DogBreedDTO dogBreedDTO) throws URISyntaxException {
        log.debug("REST request to save DogBreed : {}", dogBreedDTO);
        if (dogBreedDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new dogBreed cannot already have an ID")).body(null);
        }
        DogBreedDTO result = dogBreedService.save(dogBreedDTO);
        return ResponseEntity.created(new URI("/api/dog-breeds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dog-breeds : Updates an existing dogBreed.
     *
     * @param dogBreedDTO the dogBreedDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dogBreedDTO,
     * or with status 400 (Bad Request) if the dogBreedDTO is not valid,
     * or with status 500 (Internal Server Error) if the dogBreedDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dog-breeds")
    @Timed
    public ResponseEntity<DogBreedDTO> updateDogBreed(@RequestBody DogBreedDTO dogBreedDTO) throws URISyntaxException {
        log.debug("REST request to update DogBreed : {}", dogBreedDTO);
        if (dogBreedDTO.getId() == null) {
            return createDogBreed(dogBreedDTO);
        }
        DogBreedDTO result = dogBreedService.save(dogBreedDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dogBreedDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dog-breeds : get all the dogBreeds.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dogBreeds in body
     */
    @GetMapping("/dog-breeds")
    @Timed
    public List<DogBreedDTO> getAllDogBreeds() {
        log.debug("REST request to get all DogBreeds");
        return dogBreedService.findAll();
    }

    /**
     * GET  /dog-breeds/:id : get the "id" dogBreed.
     *
     * @param id the id of the dogBreedDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dogBreedDTO, or with status 404 (Not Found)
     */
    @GetMapping("/dog-breeds/{id}")
    @Timed
    public ResponseEntity<DogBreedDTO> getDogBreed(@PathVariable Long id) {
        log.debug("REST request to get DogBreed : {}", id);
        DogBreedDTO dogBreedDTO = dogBreedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dogBreedDTO));
    }

    /**
     * DELETE  /dog-breeds/:id : delete the "id" dogBreed.
     *
     * @param id the id of the dogBreedDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dog-breeds/{id}")
    @Timed
    public ResponseEntity<Void> deleteDogBreed(@PathVariable Long id) {
        log.debug("REST request to delete DogBreed : {}", id);
        dogBreedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
