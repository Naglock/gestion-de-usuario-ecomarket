package cl.ecomarket.usermanagement.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.ecomarket.usermanagement.model.Permiso;

@Repository
public interface PermisoDao extends JpaRepository<Permiso, Long>{
    Permiso findByNombre(String nombre);

}
