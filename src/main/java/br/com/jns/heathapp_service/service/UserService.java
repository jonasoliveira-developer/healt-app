package br.com.jns.heathapp_service.service;

import br.com.jns.heathapp_service.domain.UserDomain;
import br.com.jns.heathapp_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

        public UserDomain findById(final String id) {
            return repository.findById(id).orElse(null);
        }
}
