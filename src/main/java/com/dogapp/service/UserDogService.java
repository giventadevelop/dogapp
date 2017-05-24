package com.dogapp.service;

import com.dogapp.service.dto.UserDogDTO;
import java.util.List;

/**
 * Service Interface for managing UserDog.
 */
public interface UserDogService {

    /**
     * Save a userDog.
     *
     * @param userDogDTO the entity to save
     * @return the persisted entity
     */
    UserDogDTO save(UserDogDTO userDogDTO);

    /**
     *  Get all the userDogs.
     *  
     *  @return the list of entities
     */
    List<UserDogDTO> findAll();

    /**
     *  Get the "id" userDog.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UserDogDTO findOne(Long id);

    /**
     *  Delete the "id" userDog.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
