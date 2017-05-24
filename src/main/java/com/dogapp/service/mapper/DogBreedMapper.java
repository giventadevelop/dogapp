package com.dogapp.service.mapper;

import com.dogapp.domain.*;
import com.dogapp.service.dto.DogBreedDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity DogBreed and its DTO DogBreedDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DogBreedMapper {

    DogBreedDTO dogBreedToDogBreedDTO(DogBreed dogBreed);

    List<DogBreedDTO> dogBreedsToDogBreedDTOs(List<DogBreed> dogBreeds);

    DogBreed dogBreedDTOToDogBreed(DogBreedDTO dogBreedDTO);

    List<DogBreed> dogBreedDTOsToDogBreeds(List<DogBreedDTO> dogBreedDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default DogBreed dogBreedFromId(Long id) {
        if (id == null) {
            return null;
        }
        DogBreed dogBreed = new DogBreed();
        dogBreed.setId(id);
        return dogBreed;
    }
    

}
