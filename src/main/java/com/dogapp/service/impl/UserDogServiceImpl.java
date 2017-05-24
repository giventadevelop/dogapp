package com.dogapp.service.impl;

import com.dogapp.service.UserDogService;
import com.dogapp.domain.UserDog;
import com.dogapp.repository.UserDogRepository;
import com.dogapp.service.dto.UserDogDTO;
import com.dogapp.service.mapper.UserDogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing UserDog.
 */
@Service
@Transactional
public class UserDogServiceImpl implements UserDogService{

    private final Logger log = LoggerFactory.getLogger(UserDogServiceImpl.class);
    
    private final UserDogRepository userDogRepository;

    private final UserDogMapper userDogMapper;

    public UserDogServiceImpl(UserDogRepository userDogRepository, UserDogMapper userDogMapper) {
        this.userDogRepository = userDogRepository;
        this.userDogMapper = userDogMapper;
    }

    /**
     * Save a userDog.
     *
     * @param userDogDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserDogDTO save(UserDogDTO userDogDTO) {
        log.debug("Request to save UserDog : {}", userDogDTO);
        UserDog userDog = userDogMapper.userDogDTOToUserDog(userDogDTO);
        userDog = userDogRepository.save(userDog);
        UserDogDTO result = userDogMapper.userDogToUserDogDTO(userDog);
        return result;
    }

    /**
     *  Get all the userDogs.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserDogDTO> findAll() {
        log.debug("Request to get all UserDogs");
        List<UserDogDTO> result = userDogRepository.findAll().stream()
            .map(userDogMapper::userDogToUserDogDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one userDog by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserDogDTO findOne(Long id) {
        log.debug("Request to get UserDog : {}", id);
        UserDog userDog = userDogRepository.findOne(id);
        UserDogDTO userDogDTO = userDogMapper.userDogToUserDogDTO(userDog);
        return userDogDTO;
    }

    /**
     *  Delete the  userDog by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserDog : {}", id);
        userDogRepository.delete(id);
    }
}
