package com.svp.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import com.svp.domain.enumeration.BillEvent;

/**
 * A DTO for the {@link com.svp.domain.BillAudit} entity.
 */
public class BillAuditDTO implements Serializable {

    private Long id;

    private LocalDate date;

    private String message;

    private BillEvent event;


    private Long billId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BillEvent getEvent() {
        return event;
    }

    public void setEvent(BillEvent event) {
        this.event = event;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BillAuditDTO billAuditDTO = (BillAuditDTO) o;
        if (billAuditDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), billAuditDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BillAuditDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", message='" + getMessage() + "'" +
            ", event='" + getEvent() + "'" +
            ", bill=" + getBillId() +
            "}";
    }
}
