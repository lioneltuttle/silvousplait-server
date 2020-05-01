package com.svp.service.dto;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.svp.domain.Summary} entity.
 */

@ToString
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class SummaryDTO implements Serializable {

    private Long id;

    private Integer hits;

    private Integer missed;

    private Double currentBill;

    private Double rating;

}
