package com.svp.service.dto;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.svp.domain.SubscriptionType} entity.
 */
@ToString
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionTypeDTO implements Serializable {

    private Long id;

    private String type;
}
