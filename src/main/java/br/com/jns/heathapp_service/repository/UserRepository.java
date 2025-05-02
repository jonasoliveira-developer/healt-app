package br.com.jns.heathapp_service.repository;

import br.com.jns.heathapp_service.domain.UserDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface  UserRepository extends JpaRepository<UserDomain, String> {
    Optional<UserDomain> findByEmail(final String email);

    void deleteByEmail(final String emailValid);
}
