package br.com.jns.heathapp_service.models.mapper;

import br.com.jns.heathapp_service.domain.UserDomain;
import br.com.jns.heathapp_service.models.responses.UserResponse;
import org.mapstruct.Mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE,
        nullValueCheckStrategy = ALWAYS
)

public interface UserMapper {

    UserResponse fromEntity(final UserDomain domain);
}
