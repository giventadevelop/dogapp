package com.dogapp.repository;

import com.dogapp.domain.Dog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Dog entity.
 */
@SuppressWarnings("unused")
public interface DogRepository extends JpaRepository<Dog,Long> {

}
