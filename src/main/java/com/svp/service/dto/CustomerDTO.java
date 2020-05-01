package com.svp.service.dto;
import java.io.Serializable;
import java.util.Objects;
import lombok.*;
/**
 * A DTO for the {@link com.svp.domain.Customer} entity.
 */
@ToString
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private String deviceRegistrationId;

    private String phoneNumber;

    private String location;

}
