package com.svp.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.svp.domain.Summary} entity.
 */
public class SummaryDTO implements Serializable {

    private Long id;

    private Integer hits;

    private Integer missed;

    private Double currentBill;

    private Double rating;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public Integer getMissed() {
        return missed;
    }

    public void setMissed(Integer missed) {
        this.missed = missed;
    }

    public Double getCurrentBill() {
        return currentBill;
    }

    public void setCurrentBill(Double currentBill) {
        this.currentBill = currentBill;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SummaryDTO summaryDTO = (SummaryDTO) o;
        if (summaryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), summaryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SummaryDTO{" +
            "id=" + getId() +
            ", hits=" + getHits() +
            ", missed=" + getMissed() +
            ", currentBill=" + getCurrentBill() +
            ", rating=" + getRating() +
            "}";
    }
}
