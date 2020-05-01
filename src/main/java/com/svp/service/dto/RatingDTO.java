package com.svp.service.dto;
import lombok.*;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.svp.domain.Rating} entity.
 */
@ToString
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class RatingDTO implements Serializable {

    private Long id;

    private Double value;

    private LocalDate date;

    private String comment;

    private Long companyId;

}
