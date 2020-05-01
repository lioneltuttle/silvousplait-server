package com.svp.service.dto;
import java.io.Serializable;
import java.util.Objects;
import lombok.*;
/**
 * A DTO for the {@link com.svp.domain.CompanyLocation} entity.
 */
@ToString
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CompanyLocationDTO implements Serializable {

    private Long id;

    private String adresse;

    private Long professionalId;

}
