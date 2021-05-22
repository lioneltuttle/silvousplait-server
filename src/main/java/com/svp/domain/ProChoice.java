package com.svp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ProChoice.
 */
@Entity
@Table(name = "pro_choice")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProChoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "location")
    private String location;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lng")
    private Double lng;

    @Column(name = "device_registration_id")
    private String deviceRegistrationId;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "come_over")
    private Boolean comeOver;

    @OneToOne
    @JoinColumn(unique = true)
    private Professional choice;

    @OneToOne
    @JoinColumn(unique = true)
    private ProRequest request;

    @OneToOne
    @JoinColumn(unique = true)
    private Rating rating;

    @ManyToOne
    @JsonIgnoreProperties("choices")
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public ProChoice location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getLat() {
        return lat;
    }

    public ProChoice lat(Double lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public ProChoice lng(Double lng) {
        this.lng = lng;
        return this;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getDeviceRegistrationId() {
        return deviceRegistrationId;
    }

    public ProChoice deviceRegistrationId(String deviceRegistrationId) {
        this.deviceRegistrationId = deviceRegistrationId;
        return this;
    }

    public void setDeviceRegistrationId(String deviceRegistrationId) {
        this.deviceRegistrationId = deviceRegistrationId;
    }

    public LocalDate getDate() {
        return date;
    }

    public ProChoice date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean isComeOver() {
        return comeOver;
    }

    public ProChoice comeOver(Boolean comeOver) {
        this.comeOver = comeOver;
        return this;
    }

    public void setComeOver(Boolean comeOver) {
        this.comeOver = comeOver;
    }

    public Professional getChoice() {
        return choice;
    }

    public ProChoice choice(Professional professional) {
        this.choice = professional;
        return this;
    }

    public void setChoice(Professional professional) {
        this.choice = professional;
    }

    public ProRequest getRequest() {
        return request;
    }

    public ProChoice request(ProRequest proRequest) {
        this.request = proRequest;
        return this;
    }

    public void setRequest(ProRequest proRequest) {
        this.request = proRequest;
    }

    public Rating getRating() {
        return rating;
    }

    public ProChoice rating(Rating rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Customer getCustomer() {
        return customer;
    }

    public ProChoice customer(Customer customer) {
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
        if (!(o instanceof ProChoice)) {
            return false;
        }
        return id != null && id.equals(((ProChoice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProChoice{" +
            "id=" + getId() +
            ", location='" + getLocation() + "'" +
            ", lat=" + getLat() +
            ", lng=" + getLng() +
            ", deviceRegistrationId='" + getDeviceRegistrationId() + "'" +
            ", date='" + getDate() + "'" +
            ", comeOver='" + isComeOver() + "'" +
            "}";
    }
}
