package com.dogapp.repository;

import com.dogapp.domain.Dog;
import com.dogapp.service.dto.GroupByDogBreedDTO;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Dog entity.
 */
@SuppressWarnings("unused")
public interface DogRepository extends JpaRepository<Dog,Long> {
	
	
	/* @Query(value = "select dg_breed.breed_name, count(dg_breed.breed_name) as dogbreed_count from dog dg "+ 
        "inner join dog_breed dg_breed on dg.dogbreed_id = dg_breed.id group by dg_breed.breed_name" , nativeQuery = true)
	    List<GroupByDogBreedDTO> getGroupByDogBreed();*/
	/*Below give the full package name for the DTO class e.g. new com.dogapp.service.dto.GroupByDogBreedDTO .
	  If you don’t do this, Spring Data JPA will throw an exception because it cannot locate the DTO class.*/
	 @Query("select new com.dogapp.service.dto.GroupByDogBreedDTO(dg_breed.breedName, count(dg_breed.breedName)) from Dog dg "+ 
		        "INNER JOIN DogBreed dg_breed ON dg.dogbreed = dg_breed.id GROUP BY dg_breed.breedName")
			    List<GroupByDogBreedDTO> getGroupByDogBreed();

}
