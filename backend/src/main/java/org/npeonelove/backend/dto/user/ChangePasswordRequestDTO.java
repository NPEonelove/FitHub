package org.npeonelove.backend.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Data Transfer Object for password change request")
public class ChangePasswordRequestDTO {

    @NotBlank(message = "Enter an old password")
    @Size(min = 6, max = 32, message = "The length of the old password should be from 6 to 32 characters")
    @Schema(
            description = "User's current password",
            example = "CurrentSecurePass123!",
            minLength = 6,
            maxLength = 32,
            requiredMode = Schema.RequiredMode.REQUIRED,
            format = "password"
    )
    private String oldPassword;

    @NotBlank(message = "Enter new password")
    @Size(min = 6, max = 32, message = "The length of the new password should be from 6 to 32 characters")
    @Schema(
            description = "New password to replace the current one",
            example = "NewSecurePass456!",
            minLength = 6,
            maxLength = 32,
            requiredMode = Schema.RequiredMode.REQUIRED,
            format = "password"
    )
    private String newPassword;
}