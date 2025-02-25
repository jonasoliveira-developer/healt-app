package br.com.jns.heathapp_service.service;

import br.com.jns.heathapp_service.domain.UserDomain;
import br.com.jns.heathapp_service.models.exceptions.ObjectNotFoundException;
import br.com.jns.heathapp_service.models.mapper.UserMapper;
import br.com.jns.heathapp_service.models.request.CreateUserRequest;
import br.com.jns.heathapp_service.models.request.UpdateUserRequest;
import br.com.jns.heathapp_service.models.response.UserResponse;
import br.com.jns.heathapp_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final BCryptPasswordEncoder encoder;

        public UserResponse findById(final String id) {
            return mapper.fromEntity(find(id));
        }

        public void save(CreateUserRequest request) {
            verifyIfEmailAlreadyExists(request.email(),null);

                repository.save(mapper.fromRequest(request)
                        .withPassword(encoder.encode(request.password()))
                );
        }

        public List<UserResponse> findAll() {
                return repository.findAll()
                        .stream()
                        .map(mapper::fromEntity)
                        .toList();
        }

        public UserResponse update( final UpdateUserRequest request, final String id) {
            var entity = find(id);
            verifyIfEmailAlreadyExists(request.email(), id);

            return mapper.fromEntity(repository.save(mapper.update(request, entity)
                    .withPassword(request.password() != null
                            ? encoder.encode(request.password())
                            : entity.getPassword())
            ));
        }

        private UserDomain find(String id) {
            return repository.findById(id)
                    .orElseThrow(() -> new ObjectNotFoundException(
                            "Obeject not found Id: " + id + ", Type: " + UserResponse.class.getSimpleName())
            );
        }

        private void verifyIfEmailAlreadyExists(final String email, final String id) {
            repository.findByEmail(email)
                    .filter(user -> !user.getId().equals(id))
                    .ifPresent(user -> {
                        throw new DataIntegrityViolationException("Email: ["+email+"] already exists");
                    });
        }
}
