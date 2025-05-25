package cl.ecomarket.usermanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.ecomarket.usermanagement.model.Usuario;

public interface UsuarioDao extends JpaRepository<Usuario, Long> {

}
