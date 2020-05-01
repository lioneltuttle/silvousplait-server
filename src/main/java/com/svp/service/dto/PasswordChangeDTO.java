package com.svp.service.dto;

import lombok.*;

/**
 * A DTO representing a password change required data - current and new password.
 */
@ToString
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeDTO {

    private String currentPassword;

    private String newPassword;
}
