package com.svp.service.dto;
import lombok.*;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.svp.domain.Company} entity.
 */
@ToString
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDTO implements Serializable {

    private Long id;

    private String name;

    private LocalDate creationDate;

    private Long companyTypeId;

    private Long subscriptionTypeId;

}
