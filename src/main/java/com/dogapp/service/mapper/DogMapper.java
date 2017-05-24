package com.dogapp.service.mapper;

import com.dogapp.domain.*;
import com.dogapp.service.dto.DogDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Dog and its DTO DogDTO.
 */
@Mapper(componentModel = "spring", uses = {DogBreedMapper.class, })
public interface DogMapper {

    @Mapping(source = "dogbreed.id", target = "dogbreedId")
    DogDTO dogToDogDTO(Dog dog);

    List<DogDTO> dogsToDogDTOs(List<Dog> dogs);

    @Mapping(source = "dogbreedId", target = "dogbreed")
    Dog dogDTOToDog(DogDTO dogDTO);

    List<Dog> dogDTOsToDogs(List<DogDTO> dogDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Dog dogFromId(Long id) {
        if (id == null) {
            return null;
        }
        Dog dog = new Dog();
        dog.setId(id);
        return dog;
    }
    

}
