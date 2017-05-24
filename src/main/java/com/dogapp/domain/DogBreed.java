package com.dogapp.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * The DogBreed entity.
 */
@ApiModel(description = "The DogBreed entity.")
@Entity
@Table(name = "dog_breed")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DogBreed implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "breed_name")
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

    public DogBreed breedName(String breedName) {
        this.breedName = breedName;
        return this;
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
        DogBreed dogBreed = (DogBreed) o;
        if (dogBreed.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, dogBreed.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DogBreed{" +
            "id=" + id +
            ", breedName='" + breedName + "'" +
            '}';
    }
}
