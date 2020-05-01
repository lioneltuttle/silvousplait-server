package com.svp.service.dto;
import lombok.*;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.svp.domain.Professional} entity.
 */
@ToString
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ProfessionalDTO implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private LocalDate creationDate;

    private Boolean up;

    private Boolean active;

    private Long detailsId;

    private Long locationId;

}
