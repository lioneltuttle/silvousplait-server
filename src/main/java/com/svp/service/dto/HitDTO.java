package com.svp.service.dto;
import lombok.*;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.svp.domain.Hit} entity.
 */
@ToString
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class HitDTO implements Serializable {

    private Long id;

    private LocalDate date;

    private Boolean answered;

    private Boolean transformed;

    private Long professionalId;

    private Long customerId;

}
