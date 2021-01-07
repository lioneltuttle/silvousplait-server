package com.svp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.svp.domain.enumeration.ProfessionalEvent;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ProfessionalAudit.
 */
@Entity
@Table(name = "professional_audit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProfessionalAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "message")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "event")
    private ProfessionalEvent event;

    @ManyToOne
    @JsonIgnoreProperties("professionalAudits")
    private Professional professional;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public ProfessionalAudit date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public ProfessionalAudit message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ProfessionalEvent getEvent() {
        return event;
    }

    public ProfessionalAudit event(ProfessionalEvent event) {
        this.event = event;
        return this;
    }

    public void setEvent(ProfessionalEvent event) {
        this.event = event;
    }

    public Professional getProfessional() {
        return professional;
    }

    public ProfessionalAudit professional(Professional professional) {
        this.professional = professional;
        return this;
    }

    public void setProfessional(Professional professional) {
        this.professional = professional;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProfessionalAudit)) {
            return false;
        }
        return id != null && id.equals(((ProfessionalAudit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProfessionalAudit{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", message='" + getMessage() + "'" +
            ", event='" + getEvent() + "'" +
            "}";
    }
}
