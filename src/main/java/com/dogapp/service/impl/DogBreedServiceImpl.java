package com.dogapp.service.impl;

import com.dogapp.service.DogBreedService;
import com.dogapp.domain.DogBreed;
import com.dogapp.repository.DogBreedRepository;
import com.dogapp.service.dto.DogBreedDTO;
import com.dogapp.service.mapper.DogBreedMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing DogBreed.
 */
@Service
@Transactional
public class DogBreedServiceImpl implements DogBreedService{

    private final Logger log = LoggerFactory.getLogger(DogBreedServiceImpl.class);
    
    private final DogBreedRepository dogBreedRepository;

    private final DogBreedMapper dogBreedMapper;

    public DogBreedServiceImpl(DogBreedRepository dogBreedRepository, DogBreedMapper dogBreedMapper) {
        this.dogBreedRepository = dogBreedRepository;
        this.dogBreedMapper = dogBreedMapper;
    }

    /**
     * Save a dogBreed.
     *
     * @param dogBreedDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DogBreedDTO save(DogBreedDTO dogBreedDTO) {
        log.debug("Request to save DogBreed : {}", dogBreedDTO);
        DogBreed dogBreed = dogBreedMapper.dogBreedDTOToDogBreed(dogBreedDTO);
        dogBreed = dogBreedRepository.save(dogBreed);
        DogBreedDTO result = dogBreedMapper.dogBreedToDogBreedDTO(dogBreed);
        return result;
    }

    /**
     *  Get all the dogBreeds.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DogBreedDTO> findAll() {
        log.debug("Request to get all DogBreeds");
        List<DogBreedDTO> result = dogBreedRepository.findAll().stream()
            .map(dogBreedMapper::dogBreedToDogBreedDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one dogBreed by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DogBreedDTO findOne(Long id) {
        log.debug("Request to get DogBreed : {}", id);
        DogBreed dogBreed = dogBreedRepository.findOne(id);
        DogBreedDTO dogBreedDTO = dogBreedMapper.dogBreedToDogBreedDTO(dogBreed);
        return dogBreedDTO;
    }

    /**
     *  Delete the  dogBreed by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DogBreed : {}", id);
        dogBreedRepository.delete(id);
    }
}
