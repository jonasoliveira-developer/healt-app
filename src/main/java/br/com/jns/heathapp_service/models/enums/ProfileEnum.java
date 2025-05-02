package br.com.jns.heathapp_service.models.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ProfileEnum {

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_CLIENT("ROLE_CLIENT");

    private final String description;

    public static ProfileEnum toEnum(final String description) {
        return Arrays.stream(ProfileEnum.values())
                .filter(profileEnum -> profileEnum.getDescription().equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid description: " + description));
    }
}
