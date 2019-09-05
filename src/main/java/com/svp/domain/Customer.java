package com.svp.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "device_registration_id")
    private String deviceRegistrationId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProRequest> requests = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProChoice> choices = new HashSet<>();

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

    public Customer firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Customer lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDeviceRegistrationId() {
        return deviceRegistrationId;
    }

    public Customer deviceRegistrationId(String deviceRegistrationId) {
        this.deviceRegistrationId = deviceRegistrationId;
        return this;
    }

    public void setDeviceRegistrationId(String deviceRegistrationId) {
        this.deviceRegistrationId = deviceRegistrationId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Customer phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public Customer location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<ProRequest> getRequests() {
        return requests;
    }

    public Customer requests(Set<ProRequest> proRequests) {
        this.requests = proRequests;
        return this;
    }

    public Customer addRequests(ProRequest proRequest) {
        this.requests.add(proRequest);
        proRequest.setCustomer(this);
        return this;
    }

    public Customer removeRequests(ProRequest proRequest) {
        this.requests.remove(proRequest);
        proRequest.setCustomer(null);
        return this;
    }

    public void setRequests(Set<ProRequest> proRequests) {
        this.requests = proRequests;
    }

    public Set<ProChoice> getChoices() {
        return choices;
    }

    public Customer choices(Set<ProChoice> proChoices) {
        this.choices = proChoices;
        return this;
    }

    public Customer addChoices(ProChoice proChoice) {
        this.choices.add(proChoice);
        proChoice.setCustomer(this);
        return this;
    }

    public Customer removeChoices(ProChoice proChoice) {
        this.choices.remove(proChoice);
        proChoice.setCustomer(null);
        return this;
    }

    public void setChoices(Set<ProChoice> proChoices) {
        this.choices = proChoices;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", deviceRegistrationId='" + getDeviceRegistrationId() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", location='" + getLocation() + "'" +
            "}";
    }
}
