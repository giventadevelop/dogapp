package com.dogapp.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * The Dog entity.
 */
@ApiModel(description = "The Dog entity.")
@Entity
@Table(name = "dog")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Dog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dog_name")
    private String dogName;

    @Column(name = "votes")
    private Long votes;

    @Lob
    @Column(name = "dog_picture")
    private byte[] dogPicture;

    @Column(name = "dog_picture_content_type")
    private String dogPictureContentType;

    @OneToOne
    @JoinColumn(unique = true)
    private DogBreed dogbreed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDogName() {
        return dogName;
    }

    public Dog dogName(String dogName) {
        this.dogName = dogName;
        return this;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public Long getVotes() {
        return votes;
    }

    public Dog votes(Long votes) {
        this.votes = votes;
        return this;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }

    public byte[] getDogPicture() {
        return dogPicture;
    }

    public Dog dogPicture(byte[] dogPicture) {
        this.dogPicture = dogPicture;
        return this;
    }

    public void setDogPicture(byte[] dogPicture) {
        this.dogPicture = dogPicture;
    }

    public String getDogPictureContentType() {
        return dogPictureContentType;
    }

    public Dog dogPictureContentType(String dogPictureContentType) {
        this.dogPictureContentType = dogPictureContentType;
        return this;
    }

    public void setDogPictureContentType(String dogPictureContentType) {
        this.dogPictureContentType = dogPictureContentType;
    }

    public DogBreed getDogbreed() {
        return dogbreed;
    }

    public Dog dogbreed(DogBreed dogBreed) {
        this.dogbreed = dogBreed;
        return this;
    }

    public void setDogbreed(DogBreed dogBreed) {
        this.dogbreed = dogBreed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dog dog = (Dog) o;
        if (dog.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, dog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Dog{" +
            "id=" + id +
            ", dogName='" + dogName + "'" +
            ", votes='" + votes + "'" +
            ", dogPicture='" + dogPicture + "'" +
            ", dogPictureContentType='" + dogPictureContentType + "'" +
            '}';
    }
}
