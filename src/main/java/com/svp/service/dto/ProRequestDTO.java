package com.svp.service.dto;
import lombok.*;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.svp.domain.ProRequest} entity.
 */
@ToString
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ProRequestDTO implements Serializable {

    private Long id;

    private String location;

    private String deviceRegistrationId;

    private LocalDate date;

    private Long companyTypeId;

    private Long customerId;

}
