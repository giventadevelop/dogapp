package com.dogapp.service.mapper;

import com.dogapp.domain.*;
import com.dogapp.service.dto.UserDogDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity UserDog and its DTO UserDogDTO.
 */
@Mapper(componentModel = "spring", uses = {DogMapper.class, })
public interface UserDogMapper {

    @Mapping(source = "dog.id", target = "dogId")
    UserDogDTO userDogToUserDogDTO(UserDog userDog);

    List<UserDogDTO> userDogsToUserDogDTOs(List<UserDog> userDogs);

    @Mapping(source = "dogId", target = "dog")
    UserDog userDogDTOToUserDog(UserDogDTO userDogDTO);

    List<UserDog> userDogDTOsToUserDogs(List<UserDogDTO> userDogDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default UserDog userDogFromId(Long id) {
        if (id == null) {
            return null;
        }
        UserDog userDog = new UserDog();
        userDog.setId(id);
        return userDog;
    }
    

}
