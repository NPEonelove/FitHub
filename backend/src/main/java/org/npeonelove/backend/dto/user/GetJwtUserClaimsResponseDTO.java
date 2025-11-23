package org.npeonelove.backend.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.npeonelove.backend.model.user.UserRoleEnum;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "DTO containing JWT claims information for authenticated user")
public class GetJwtUserClaimsResponseDTO {

    @Schema(
            description = "Unique identifier of the authenticated user",
            example = "550e8400-e29b-41d4-a716-446655440000",
            format = "uuid"
    )
    private UUID userId;

    @Schema(
            description = "Role of the authenticated user",
            example = "USER",
            implementation = UserRoleEnum.class,
            allowableValues = {"USER", "ADMIN"}
    )
    private UserRoleEnum role;
}