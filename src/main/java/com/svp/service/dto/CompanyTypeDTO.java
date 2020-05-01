package com.svp.service.dto;
import java.io.Serializable;
import java.util.Objects;
import lombok.*;
/**
 * A DTO for the {@link com.svp.domain.CompanyType} entity.
 */
@ToString
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CompanyTypeDTO implements Serializable {

    private Long id;

    private String type;

}
