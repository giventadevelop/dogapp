package com.dogapp.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Dog entity.
 */
public class DogUserDogDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String userName;
    
    private String loggedInUserName;
    
    private String base64EncodedImg;
    
    private byte[] dogPicture;
    
    
    public DogUserDogDTO(Long id, String userName,byte[] dogPicture) {
		super();
		this.id = id;
		this.userName = userName;
		this.setDogPicture(dogPicture);
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoggedInUserName() {
		return loggedInUserName;
	}

	public void setLoggedInUserName(String loggedInUserName) {
		this.loggedInUserName = loggedInUserName;
	}
	
	/**
	 * @return the base64EncodedImg
	 */
	public String getBase64EncodedImg() {
		return base64EncodedImg;
	}

	/**
	 * @param base64EncodedImg the base64EncodedImg to set
	 */
	public void setBase64EncodedImg(String base64EncodedImg) {
		this.base64EncodedImg = base64EncodedImg;
	}

	

	/**
	 * @return the dogPicture
	 */
	public byte[] getDogPicture() {
		return dogPicture;
	}

	/**
	 * @param dogPicture the dogPicture to set
	 */
	public void setDogPicture(byte[] dogPicture) {
		this.dogPicture = dogPicture;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DogUserDogDTO dogDTO = (DogUserDogDTO) o;

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
            ", userName='" + userName + "'" +
            ", loggedInUserName='" + loggedInUserName + "'" +
            '}';
    }

	
}
