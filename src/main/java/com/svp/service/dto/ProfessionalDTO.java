package com.svp.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
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

    private String address;

    private Double lat;

    private Double lng;

    private String phoneNumber;

    private Double hourlyRate;

    private Boolean onMobility;

    private Long userId;

    private Long companyId;

    private Long companyTypeId;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getCompanyTypeId() {
        return companyTypeId;
    }

    public void setCompanyTypeId(Long companyTypeId) {
        this.companyTypeId = companyTypeId;
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
            ", address='" + getAddress() + "'" +
            ", lat=" + getLat() +
            ", lng=" + getLng() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", hourlyRate=" + getHourlyRate() +
            ", onMobility='" + isOnMobility() + "'" +
            ", userId=" + getUserId() +
            ", company=" + getCompanyId() +
            "}";
    }
}
