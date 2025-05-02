package br.com.jns.heathapp_service.repository;

import br.com.jns.heathapp_service.domain.RefreshTokenDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenDomain, String> {

}
