package com.svp.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.svp.domain.CompanyLocation} entity.
 */
public class CompanyLocationDTO implements Serializable {

    private Long id;

    private String adresse;


    private Long professionalId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Long getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(Long companyId) {
        this.professionalId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompanyLocationDTO companyLocationDTO = (CompanyLocationDTO) o;
        if (companyLocationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyLocationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyLocationDTO{" +
            "id=" + getId() +
            ", adresse='" + getAdresse() + "'" +
            ", professional=" + getProfessionalId() +
            "}";
    }
}
