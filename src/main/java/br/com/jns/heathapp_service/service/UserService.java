package br.com.jns.heathapp_service.service;

import br.com.jns.heathapp_service.models.exceptions.ObjectNotFoundException;
import br.com.jns.heathapp_service.models.mapper.UserMapper;
import br.com.jns.heathapp_service.models.request.CreateUserRequest;
import br.com.jns.heathapp_service.models.response.UserResponse;
import br.com.jns.heathapp_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

        public UserResponse findById(final String id) {
            return mapper.fromEntity(
                    repository.findById(id).orElseThrow(
                            () -> new ObjectNotFoundException(
                                    "Obeject not found Id: " + id + ", Type: " + UserResponse.class.getSimpleName())
                    )
            );
        }

    public void save(CreateUserRequest request) {
            repository.save(mapper.fromRequest(request));
    }
}
