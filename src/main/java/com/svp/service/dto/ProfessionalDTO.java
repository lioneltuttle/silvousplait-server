package com.svp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.svp.domain.Professional} entity.
 */
public class ProfessionalDTO implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private LocalDate creationDate;

    private Boolean up;

    private Boolean active;


    private Long detailsId;

    private Long locationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean isUp() {
        return up;
    }

    public void setUp(Boolean up) {
        this.up = up;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getDetailsId() {
        return detailsId;
    }

    public void setDetailsId(Long professionalDetailsId) {
        this.detailsId = professionalDetailsId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long companyLocationId) {
        this.locationId = companyLocationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProfessionalDTO professionalDTO = (ProfessionalDTO) o;
        if (professionalDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), professionalDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProfessionalDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", up='" + isUp() + "'" +
            ", active='" + isActive() + "'" +
            ", details=" + getDetailsId() +
            ", location=" + getLocationId() +
            "}";
    }
}
