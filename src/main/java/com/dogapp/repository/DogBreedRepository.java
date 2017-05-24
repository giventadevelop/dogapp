package com.dogapp.repository;

import com.dogapp.domain.DogBreed;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DogBreed entity.
 */
@SuppressWarnings("unused")
public interface DogBreedRepository extends JpaRepository<DogBreed,Long> {

}
