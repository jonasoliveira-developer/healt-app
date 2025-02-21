package br.com.jns.heathapp_service.models.mapper;

import br.com.jns.heathapp_service.domain.UserDomain;
import br.com.jns.heathapp_service.models.request.CreateUserRequest;
import br.com.jns.heathapp_service.models.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE,
        nullValueCheckStrategy = ALWAYS
)

public interface UserMapper {

    UserResponse fromEntity(final UserDomain domain);

    @Mapping(target = "id", ignore = true)
    UserDomain fromRequest(CreateUserRequest request);
}
