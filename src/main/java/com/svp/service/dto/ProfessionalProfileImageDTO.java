package com.svp.service.dto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.svp.domain.ProfessionalProfileImage} entity.
 */
public class ProfessionalProfileImageDTO implements Serializable {

    private Long id;

    private Long proId;

    @Lob
    private byte[] image;

    private String imageContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProId() {
        return proId;
    }

    public void setProId(Long proId) {
        this.proId = proId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProfessionalProfileImageDTO professionalProfileImageDTO = (ProfessionalProfileImageDTO) o;
        if (professionalProfileImageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), professionalProfileImageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProfessionalProfileImageDTO{" +
            "id=" + getId() +
            ", proId=" + getProId() +
            ", image='" + getImage() + "'" +
            "}";
    }
}
