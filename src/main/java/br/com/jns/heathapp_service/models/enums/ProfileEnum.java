package br.com.jns.heathapp_service.models.enums;


import lombok.Getter;

import java.util.Arrays;


@Getter
public enum ProfileEnum {
   ROLE_ADMIN("ROLE_ADMIN"),
   ROLE_CLIENT("ROLE_CLIENT");

    private final String description;

    ProfileEnum( String description) {
        this.description = description;
    }



    public static ProfileEnum toEnum(String description) {
        return Arrays.stream(ProfileEnum.values())
                .filter(profileEnum -> profileEnum.getDescription().equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid description " + description));
    }
}
