package com.svp.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import com.svp.domain.enumeration.ProfessionalEvent;
import lombok.*;

/**
 * A DTO for the {@link com.svp.domain.ProfessionalAudit} entity.
 */
@ToString
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ProfessionalAuditDTO implements Serializable {

    private Long id;

    private LocalDate date;

    private String message;

    private ProfessionalEvent event;

    private Long professionalId;

}
