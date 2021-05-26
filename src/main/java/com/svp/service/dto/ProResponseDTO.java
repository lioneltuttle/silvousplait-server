package com.svp.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.svp.domain.ProResponse} entity.
 */
public class ProResponseDTO implements Serializable {

    private Long id;

    private Boolean accept;


    private Long requestId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isAccept() {
        return accept;
    }

    public void setAccept(Boolean accept) {
        this.accept = accept;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long proRequestId) {
        this.requestId = proRequestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProResponseDTO proResponseDTO = (ProResponseDTO) o;
        if (proResponseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), proResponseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProResponseDTO{" +
            "id=" + getId() +
            ", accept='" + isAccept() + "'" +
            ", request=" + getRequestId() +
            "}";
    }
}
