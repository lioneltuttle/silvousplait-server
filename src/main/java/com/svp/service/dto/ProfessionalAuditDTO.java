package com.svp.service.dto;

import com.svp.domain.enumeration.ProfessionalEvent;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.svp.domain.ProfessionalAudit} entity.
 */
public class ProfessionalAuditDTO implements Serializable {

    private Long id;

    private LocalDate date;

    private String message;

    private ProfessionalEvent event;


    private Long professionalId;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ProfessionalEvent getEvent() {
        return event;
    }

    public void setEvent(ProfessionalEvent event) {
        this.event = event;
    }

    public Long getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(Long professionalId) {
        this.professionalId = professionalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProfessionalAuditDTO professionalAuditDTO = (ProfessionalAuditDTO) o;
        if (professionalAuditDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), professionalAuditDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProfessionalAuditDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", message='" + getMessage() + "'" +
            ", event='" + getEvent() + "'" +
            ", professional=" + getProfessionalId() +
            "}";
    }
}
