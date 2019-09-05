package com.svp.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Summary.
 */
@Entity
@Table(name = "summary")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Summary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "hits")
    private Integer hits;

    @Column(name = "missed")
    private Integer missed;

    @Column(name = "current_bill")
    private Double currentBill;

    @Column(name = "rating")
    private Double rating;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHits() {
        return hits;
    }

    public Summary hits(Integer hits) {
        this.hits = hits;
        return this;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public Integer getMissed() {
        return missed;
    }

    public Summary missed(Integer missed) {
        this.missed = missed;
        return this;
    }

    public void setMissed(Integer missed) {
        this.missed = missed;
    }

    public Double getCurrentBill() {
        return currentBill;
    }

    public Summary currentBill(Double currentBill) {
        this.currentBill = currentBill;
        return this;
    }

    public void setCurrentBill(Double currentBill) {
        this.currentBill = currentBill;
    }

    public Double getRating() {
        return rating;
    }

    public Summary rating(Double rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Summary)) {
            return false;
        }
        return id != null && id.equals(((Summary) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Summary{" +
            "id=" + getId() +
            ", hits=" + getHits() +
            ", missed=" + getMissed() +
            ", currentBill=" + getCurrentBill() +
            ", rating=" + getRating() +
            "}";
    }
}
