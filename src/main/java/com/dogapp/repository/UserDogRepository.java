package com.dogapp.repository;

import com.dogapp.domain.UserDog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserDog entity.
 */
@SuppressWarnings("unused")
public interface UserDogRepository extends JpaRepository<UserDog,Long> {

}
