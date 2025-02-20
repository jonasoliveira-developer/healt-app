package br.com.jns.heathapp_service.models.responses;

import br.com.jns.heathapp_service.models.enums.ProfileEnum;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

public record UserResponse(
        String id,
        String name,
        String email,
        String password,
        Set<ProfileEnum> profile
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
