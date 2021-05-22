package com.svp.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.svp.domain.ProRequest} entity.
 */
public class ProRequestDTO implements Serializable {

    private Long id;

    private String location;

    private Double lat;

    private Double lng;

    private String deviceRegistrationId;

    private LocalDate date;

    private Boolean comeOver;


    private Long companyTypeId;

    private Long customerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getDeviceRegistrationId() {
        return deviceRegistrationId;
    }

    public void setDeviceRegistrationId(String deviceRegistrationId) {
        this.deviceRegistrationId = deviceRegistrationId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean isComeOver() {
        return comeOver;
    }

    public void setComeOver(Boolean comeOver) {
        this.comeOver = comeOver;
    }

    public Long getCompanyTypeId() {
        return companyTypeId;
    }

    public void setCompanyTypeId(Long companyTypeId) {
        this.companyTypeId = companyTypeId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProRequestDTO proRequestDTO = (ProRequestDTO) o;
        if (proRequestDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), proRequestDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProRequestDTO{" +
            "id=" + getId() +
            ", location='" + getLocation() + "'" +
            ", lat=" + getLat() +
            ", lng=" + getLng() +
            ", deviceRegistrationId='" + getDeviceRegistrationId() + "'" +
            ", date='" + getDate() + "'" +
            ", comeOver='" + isComeOver() + "'" +
            ", companyType=" + getCompanyTypeId() +
            ", customer=" + getCustomerId() +
            "}";
    }
}
