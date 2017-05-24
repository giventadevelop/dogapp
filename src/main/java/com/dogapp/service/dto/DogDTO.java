package com.dogapp.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Dog entity.
 */
public class DogDTO implements Serializable {

    private Long id;

    private String dogName;

    private Long votes;

    @Lob
    private byte[] dogPicture;
    private String dogPictureContentType;

    private Long dogbreedId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }
    public Long getVotes() {
        return votes;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }
    public byte[] getDogPicture() {
        return dogPicture;
    }

    public void setDogPicture(byte[] dogPicture) {
        this.dogPicture = dogPicture;
    }

    public String getDogPictureContentType() {
        return dogPictureContentType;
    }

    public void setDogPictureContentType(String dogPictureContentType) {
        this.dogPictureContentType = dogPictureContentType;
    }

    public Long getDogbreedId() {
        return dogbreedId;
    }

    public void setDogbreedId(Long dogBreedId) {
        this.dogbreedId = dogBreedId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DogDTO dogDTO = (DogDTO) o;

        if ( ! Objects.equals(id, dogDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DogDTO{" +
            "id=" + id +
            ", dogName='" + dogName + "'" +
            ", votes='" + votes + "'" +
            ", dogPicture='" + dogPicture + "'" +
            '}';
    }
}
