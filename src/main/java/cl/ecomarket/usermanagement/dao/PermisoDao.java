package cl.ecomarket.usermanagement.dao;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.ecomarket.usermanagement.model.Permiso;

@Repository
public interface PermisoDao extends JpaRepository<Permiso, Long>{
    Optional<Permiso> findByNombre(String nombre);

}
