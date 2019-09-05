package com.svp.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.svp.domain.ProChoice} entity.
 */
public class ProChoiceDTO implements Serializable {

    private Long id;

    private String location;

    private String deviceRegistrationId;

    private LocalDate date;


    private Long choiceId;

    private Long requestId;

    private Long ratingId;

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

    public Long getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Long professionalId) {
        this.choiceId = professionalId;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long proRequestId) {
        this.requestId = proRequestId;
    }

    public Long getRatingId() {
        return ratingId;
    }

    public void setRatingId(Long ratingId) {
        this.ratingId = ratingId;
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

        ProChoiceDTO proChoiceDTO = (ProChoiceDTO) o;
        if (proChoiceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), proChoiceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProChoiceDTO{" +
            "id=" + getId() +
            ", location='" + getLocation() + "'" +
            ", deviceRegistrationId='" + getDeviceRegistrationId() + "'" +
            ", date='" + getDate() + "'" +
            ", choice=" + getChoiceId() +
            ", request=" + getRequestId() +
            ", rating=" + getRatingId() +
            ", customer=" + getCustomerId() +
            "}";
    }
}
