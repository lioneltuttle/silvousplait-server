package com.svp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ProResponse.
 */
@Entity
@Table(name = "pro_response")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "accept")
    private Boolean accept;

    @ManyToOne
    @JsonIgnoreProperties("proResponses")
    private ProRequest request;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isAccept() {
        return accept;
    }

    public ProResponse accept(Boolean accept) {
        this.accept = accept;
        return this;
    }

    public void setAccept(Boolean accept) {
        this.accept = accept;
    }

    public ProRequest getRequest() {
        return request;
    }

    public ProResponse request(ProRequest proRequest) {
        this.request = proRequest;
        return this;
    }

    public void setRequest(ProRequest proRequest) {
        this.request = proRequest;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProResponse)) {
            return false;
        }
        return id != null && id.equals(((ProResponse) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProResponse{" +
            "id=" + getId() +
            ", accept='" + isAccept() + "'" +
            "}";
    }
}
