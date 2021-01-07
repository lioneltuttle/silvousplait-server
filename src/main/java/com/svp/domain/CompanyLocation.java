package com.svp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CompanyLocation.
 */
@Entity
@Table(name = "company_location")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CompanyLocation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lng")
    private Double lng;

    @OneToMany(mappedBy = "location")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Professional> professionals = new HashSet<>();

    @ManyToOne(optional = false)
    @JsonIgnoreProperties("locations")
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }

    public CompanyLocation adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Double getLat() {
        return lat;
    }

    public CompanyLocation lat(Double lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public CompanyLocation lng(Double lng) {
        this.lng = lng;
        return this;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Set<Professional> getProfessionals() {
        return professionals;
    }

    public CompanyLocation professionals(Set<Professional> professionals) {
        this.professionals = professionals;
        return this;
    }

    public CompanyLocation addProfessionals(Professional professional) {
        this.professionals.add(professional);
        professional.setLocation(this);
        return this;
    }

    public CompanyLocation removeProfessionals(Professional professional) {
        this.professionals.remove(professional);
        professional.setLocation(null);
        return this;
    }

    public void setProfessionals(Set<Professional> professionals) {
        this.professionals = professionals;
    }

    public Company getCompany() {
        return company;
    }

    public CompanyLocation company(Company company) {
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
        if (!(o instanceof CompanyLocation)) {
            return false;
        }
        return id != null && id.equals(((CompanyLocation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CompanyLocation{" +
            "id=" + getId() +
            ", adresse='" + getAdresse() + "'" +
            ", lat=" + getLat() +
            ", lng=" + getLng() +
            "}";
    }
}
