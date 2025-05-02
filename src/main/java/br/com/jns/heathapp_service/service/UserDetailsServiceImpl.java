package br.com.jns.heathapp_service.service;

import br.com.jns.heathapp_service.models.exceptions.ObjectNotFoundException;
import br.com.jns.heathapp_service.repository.UserRepository;
import br.com.jns.heathapp_service.security.dtos.UserDetailsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final var entity = repository.findByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("User not found " + email));
        return UserDetailsDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .username(entity.getEmail())
                .password(entity.getPassword())
                .authorities(entity.getProfile().stream().map(role -> new SimpleGrantedAuthority(role.getDescription())).collect(Collectors.toSet()))
                .build();
    }
}
