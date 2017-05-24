package com.dogapp.service.impl;

import com.dogapp.service.DogService;
import com.dogapp.domain.Dog;
import com.dogapp.repository.DogRepository;
import com.dogapp.service.dto.DogDTO;
import com.dogapp.service.mapper.DogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Dog.
 */
@Service
@Transactional
public class DogServiceImpl implements DogService{

    private final Logger log = LoggerFactory.getLogger(DogServiceImpl.class);
    
    private final DogRepository dogRepository;

    private final DogMapper dogMapper;

    public DogServiceImpl(DogRepository dogRepository, DogMapper dogMapper) {
        this.dogRepository = dogRepository;
        this.dogMapper = dogMapper;
    }

    /**
     * Save a dog.
     *
     * @param dogDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DogDTO save(DogDTO dogDTO) {
        log.debug("Request to save Dog : {}", dogDTO);
        Dog dog = dogMapper.dogDTOToDog(dogDTO);
        dog = dogRepository.save(dog);
        DogDTO result = dogMapper.dogToDogDTO(dog);
        return result;
    }

    /**
     *  Get all the dogs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Dogs");
        Page<Dog> result = dogRepository.findAll(pageable);
        return result.map(dog -> dogMapper.dogToDogDTO(dog));
    }

    /**
     *  Get one dog by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DogDTO findOne(Long id) {
        log.debug("Request to get Dog : {}", id);
        Dog dog = dogRepository.findOne(id);
        DogDTO dogDTO = dogMapper.dogToDogDTO(dog);
        return dogDTO;
    }

    /**
     *  Delete the  dog by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dog : {}", id);
        dogRepository.delete(id);
    }
}
