package cl.ecomarket.usermanagement.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.ecomarket.usermanagement.model.Usuario;

public interface UsuarioDao extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);

}
