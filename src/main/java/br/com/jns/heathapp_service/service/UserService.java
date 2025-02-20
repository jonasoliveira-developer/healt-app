package br.com.jns.heathapp_service.service;

import br.com.jns.heathapp_service.models.mapper.UserMapper;
import br.com.jns.heathapp_service.models.responses.UserResponse;
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
                    repository.findById(id).orElse(null)
            );
        }
}
