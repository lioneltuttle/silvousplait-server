package com.svp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.svp.domain.enumeration.BillStatus;

/**
 * A Bill.
 */
@Entity
@Table(name = "bill")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_date")
    private LocalDate date;

    @Column(name = "amount_due")
    private Double amountDue;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BillStatus status;

    @ManyToOne
    @JsonIgnoreProperties("bills")
    private Company company;

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

    public Bill date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getAmountDue() {
        return amountDue;
    }

    public Bill amountDue(Double amountDue) {
        this.amountDue = amountDue;
        return this;
    }

    public void setAmountDue(Double amountDue) {
        this.amountDue = amountDue;
    }

    public BillStatus getStatus() {
        return status;
    }

    public Bill status(BillStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(BillStatus status) {
        this.status = status;
    }

    public Company getCompany() {
        return company;
    }

    public Bill company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bill)) {
            return false;
        }
        return id != null && id.equals(((Bill) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Bill{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", amountDue=" + getAmountDue() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
