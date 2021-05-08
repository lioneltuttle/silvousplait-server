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

    @Column(name = "address")
    private String address;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lng")
    private Double lng;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "hourly_rate")
    private Double hourlyRate;

    @Column(name = "on_mobility")
    private Boolean onMobility;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @JsonIgnoreProperties("professionals")
    private Company company;

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

    public String getAddress() {
        return address;
    }

    public Professional address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLat() {
        return lat;
    }

    public Professional lat(Double lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public Professional lng(Double lng) {
        this.lng = lng;
        return this;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Professional phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Double getHourlyRate() {
        return hourlyRate;
    }

    public Professional hourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
        return this;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Boolean isOnMobility() {
        return onMobility;
    }

    public Professional onMobility(Boolean onMobility) {
        this.onMobility = onMobility;
        return this;
    }

    public void setOnMobility(Boolean onMobility) {
        this.onMobility = onMobility;
    }

    public Long getUserId() {
        return userId;
    }

    public Professional userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Company getCompany() {
        return company;
    }

    public Professional company(Company company) {
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
            ", address='" + getAddress() + "'" +
            ", lat=" + getLat() +
            ", lng=" + getLng() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", hourlyRate=" + getHourlyRate() +
            ", onMobility='" + isOnMobility() + "'" +
            ", userId=" + getUserId() +
            "}";
    }
}
