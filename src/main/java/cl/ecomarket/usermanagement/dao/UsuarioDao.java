package cl.ecomarket.usermanagement.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.ecomarket.usermanagement.model.Usuario;

public interface UsuarioDao extends JpaRepository<Usuario, Long> {

}
