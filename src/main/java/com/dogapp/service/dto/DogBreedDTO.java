package com.dogapp.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the DogBreed entity.
 */
public class DogBreedDTO implements Serializable {

    private Long id;

    private String breedName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getBreedName() {
        return breedName;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DogBreedDTO dogBreedDTO = (DogBreedDTO) o;

        if ( ! Objects.equals(id, dogBreedDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DogBreedDTO{" +
            "id=" + id +
            ", breedName='" + breedName + "'" +
            '}';
    }
}
