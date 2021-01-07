package com.svp.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.svp.domain.CompanyLocation} entity.
 */
public class CompanyLocationDTO implements Serializable {

    private Long id;

    private String adresse;

    private Double lat;

    private Double lng;

    private CompanyDTO company;

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

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
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
            ", lat=" + getLat() +
            ", lng=" + getLng() +
            ", company=" + getCompany() +
            "}";
    }
}
