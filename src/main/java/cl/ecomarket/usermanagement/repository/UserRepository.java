package cl.ecomarket.usermanagement.repository;

import cl.ecomarket.usermanagement.model.usuario;
import org.springframework.Data.repository.JpaRepository;

public interface UserRepository extends JpaRepository<usuario, Long> {

}
