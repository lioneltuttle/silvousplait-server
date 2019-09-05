package com.svp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @OneToMany(mappedBy = "professional")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CompanyLocation> locations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("companies")
    private CompanyType companyType;

    @ManyToOne
    @JsonIgnoreProperties("companies")
    private SubscriptionType subscriptionType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Company name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Company creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Set<CompanyLocation> getLocations() {
        return locations;
    }

    public Company locations(Set<CompanyLocation> companyLocations) {
        this.locations = companyLocations;
        return this;
    }

    public Company addLocations(CompanyLocation companyLocation) {
        this.locations.add(companyLocation);
        companyLocation.setProfessional(this);
        return this;
    }

    public Company removeLocations(CompanyLocation companyLocation) {
        this.locations.remove(companyLocation);
        companyLocation.setProfessional(null);
        return this;
    }

    public void setLocations(Set<CompanyLocation> companyLocations) {
        this.locations = companyLocations;
    }

    public CompanyType getCompanyType() {
        return companyType;
    }

    public Company companyType(CompanyType companyType) {
        this.companyType = companyType;
        return this;
    }

    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public Company subscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
        return this;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return id != null && id.equals(((Company) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
