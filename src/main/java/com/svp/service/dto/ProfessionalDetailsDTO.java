package com.svp.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.svp.domain.ProfessionalDetails} entity.
 */
public class ProfessionalDetailsDTO implements Serializable {

    private Long id;

    private String phoneNumber;

    private Double hourlyRate;

    private Boolean onMobility;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Boolean isOnMobility() {
        return onMobility;
    }

    public void setOnMobility(Boolean onMobility) {
        this.onMobility = onMobility;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProfessionalDetailsDTO professionalDetailsDTO = (ProfessionalDetailsDTO) o;
        if (professionalDetailsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), professionalDetailsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProfessionalDetailsDTO{" +
            "id=" + getId() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", hourlyRate=" + getHourlyRate() +
            ", onMobility='" + isOnMobility() + "'" +
            "}";
    }
}
