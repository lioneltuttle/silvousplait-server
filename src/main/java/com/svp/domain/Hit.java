package com.svp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Hit.
 */
@Entity
@Table(name = "hit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Hit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_date")
    private LocalDate date;

    @Column(name = "answered")
    private Boolean answered;

    @Column(name = "transformed")
    private Boolean transformed;

    @ManyToOne
    @JsonIgnoreProperties("hits")
    private Professional professional;

    @ManyToOne
    @JsonIgnoreProperties("hits")
    private Customer customer;

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

    public Hit date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean isAnswered() {
        return answered;
    }

    public Hit answered(Boolean answered) {
        this.answered = answered;
        return this;
    }

    public void setAnswered(Boolean answered) {
        this.answered = answered;
    }

    public Boolean isTransformed() {
        return transformed;
    }

    public Hit transformed(Boolean transformed) {
        this.transformed = transformed;
        return this;
    }

    public void setTransformed(Boolean transformed) {
        this.transformed = transformed;
    }

    public Professional getProfessional() {
        return professional;
    }

    public Hit professional(Professional professional) {
        this.professional = professional;
        return this;
    }

    public void setProfessional(Professional professional) {
        this.professional = professional;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Hit customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hit)) {
            return false;
        }
        return id != null && id.equals(((Hit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Hit{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", answered='" + isAnswered() + "'" +
            ", transformed='" + isTransformed() + "'" +
            "}";
    }
}
