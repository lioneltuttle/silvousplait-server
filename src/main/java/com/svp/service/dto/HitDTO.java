package com.svp.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.svp.domain.Hit} entity.
 */
public class HitDTO implements Serializable {

    private Long id;

    private LocalDate date;

    private Boolean answered;

    private Boolean transformed;


    private Long professionalId;

    private Long customerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean isAnswered() {
        return answered;
    }

    public void setAnswered(Boolean answered) {
        this.answered = answered;
    }

    public Boolean isTransformed() {
        return transformed;
    }

    public void setTransformed(Boolean transformed) {
        this.transformed = transformed;
    }

    public Long getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(Long professionalId) {
        this.professionalId = professionalId;
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

        HitDTO hitDTO = (HitDTO) o;
        if (hitDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hitDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HitDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", answered='" + isAnswered() + "'" +
            ", transformed='" + isTransformed() + "'" +
            ", professional=" + getProfessionalId() +
            ", customer=" + getCustomerId() +
            "}";
    }
}
