package br.com.jns.heathapp_service.models.request;

import br.com.jns.heathapp_service.models.enums.ProfileEnum;
import lombok.With;

import java.util.Set;

@With
public record CreateUserRequest(
        String name,
        String email,
        String password,
        Set<ProfileEnum> profile
) {
}
