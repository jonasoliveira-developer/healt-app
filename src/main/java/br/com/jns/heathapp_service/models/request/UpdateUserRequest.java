package br.com.jns.heathapp_service.models.request;

import br.com.jns.heathapp_service.models.enums.ProfileEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UpdateUserRequest (
        @Schema(description = "User name", example = "Jon Do")
        @Size(min = 3, max = 50, message = "Name must contain between 3 and 50 characters")
        String name,

        @Schema(description = "User e-mail", example = "jon_do@email.com")
        @Email(message = "Email invalid")
        @Size(min = 8, max = 50, message = "Email must contain between 8 and 50 characters")
        String email,

        @Schema(description = "User password", example = "123456")
        @Size(min = 8, max = 50, message = "Password must contain between 8 and 50 characters")
        String password,

        @Schema(description = "User profiles", example = "[\"ROLE_ADMIN\", \"ROLE_CLIENT\"]")
        Set<ProfileEnum> profile
){
}
