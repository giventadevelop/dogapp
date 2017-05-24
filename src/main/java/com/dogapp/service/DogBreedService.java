package com.dogapp.service;

import com.dogapp.service.dto.DogBreedDTO;
import java.util.List;

/**
 * Service Interface for managing DogBreed.
 */
public interface DogBreedService {

    /**
     * Save a dogBreed.
     *
     * @param dogBreedDTO the entity to save
     * @return the persisted entity
     */
    DogBreedDTO save(DogBreedDTO dogBreedDTO);

    /**
     *  Get all the dogBreeds.
     *  
     *  @return the list of entities
     */
    List<DogBreedDTO> findAll();

    /**
     *  Get the "id" dogBreed.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DogBreedDTO findOne(Long id);

    /**
     *  Delete the "id" dogBreed.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
