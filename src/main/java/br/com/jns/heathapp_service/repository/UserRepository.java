package br.com.jns.heathapp_service.repository;

import br.com.jns.heathapp_service.domain.UserDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  UserRepository extends JpaRepository<UserDomain, String> {
}
