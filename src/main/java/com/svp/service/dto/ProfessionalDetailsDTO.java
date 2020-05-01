package com.svp.service.dto;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.svp.domain.ProfessionalDetails} entity.
 */
@ToString
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ProfessionalDetailsDTO implements Serializable {

    private Long id;

    private String phoneNumber;

    private Double hourlyRate;

    private Boolean onMobility;

}
