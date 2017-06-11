package com.dogapp.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the DogBreed entity.
 */
@SuppressWarnings("serial")
public class GroupByDogBreedDTO implements Serializable {

   
    private String breedName;
    
    private Long dogBreedCount;
    
   public GroupByDogBreedDTO(String breedName, Long dogBreedCount ){
	   this.breedName=breedName;
	   this.dogBreedCount=dogBreedCount;
   }

    public String getBreedName() {
        return breedName;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }

    /**
	 * @return the dogBreedCount
	 */
	public Long getDogBreedCount() {
		return dogBreedCount;
	}

	/**
	 * @param dogBreedCount the dogBreedCount to set
	 */
	public void setDogBreedCount(Long dogBreedCount) {
		this.dogBreedCount = dogBreedCount;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GroupByDogBreedDTO groupByDogBreedDTO = (GroupByDogBreedDTO) o;

        if ( ! Objects.equals(dogBreedCount, groupByDogBreedDTO.dogBreedCount)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dogBreedCount);
    }

    @Override
    public String toString() {
        return "DogBreedDTO{" +
            "dogBreedCount=" + dogBreedCount +
            ", breedName='" + breedName + "'" +
            '}';
    }
}
