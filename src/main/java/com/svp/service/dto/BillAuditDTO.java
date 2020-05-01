package com.svp.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import com.svp.domain.enumeration.BillEvent;
import lombok.*;
/**
 * A DTO for the {@link com.svp.domain.BillAudit} entity.
 */
@ToString
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class BillAuditDTO implements Serializable {

    private Long id;

    private LocalDate date;

    private String message;

    private BillEvent event;

    private Long billId;

}
