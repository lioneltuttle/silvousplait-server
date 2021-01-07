package com.svp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Professional.
 */
@Entity
@Table(name = "professional")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Professional implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "up")
    private Boolean up;

    @Column(name = "active")
    private Boolean active;

    @OneToOne
    @JoinColumn(unique = true)
    private ProfessionalDetails details;

    @ManyToOne
    @JsonIgnoreProperties("professionals")
    private CompanyLocation location;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Professional firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Professional lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Professional creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean isUp() {
        return up;
    }

    public Professional up(Boolean up) {
        this.up = up;
        return this;
    }

    public void setUp(Boolean up) {
        this.up = up;
    }

    public Boolean isActive() {
        return active;
    }

    public Professional active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ProfessionalDetails getDetails() {
        return details;
    }

    public Professional details(ProfessionalDetails professionalDetails) {
        this.details = professionalDetails;
        return this;
    }

    public void setDetails(ProfessionalDetails professionalDetails) {
        this.details = professionalDetails;
    }

    public CompanyLocation getLocation() {
        return location;
    }

    public Professional location(CompanyLocation companyLocation) {
        this.location = companyLocation;
        return this;
    }

    public void setLocation(CompanyLocation companyLocation) {
        this.location = companyLocation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Professional)) {
            return false;
        }
        return id != null && id.equals(((Professional) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Professional{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", up='" + isUp() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
