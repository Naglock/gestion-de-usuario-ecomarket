package cl.ecomarket.usermanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.ecomarket.usermanagement.dao.PermisoDao;
import cl.ecomarket.usermanagement.model.Permiso;

@Service
public class PermisoService {
    @Autowired
    private PermisoDao permisoDao;

    public List<Permiso> listarTodos() {
        return permisoDao.findAll();
    }

    public Permiso obtenerPorId(Long id) {
        return permisoDao.findById(id).orElse(null);
    }

    public Permiso guardarPermiso(Permiso permiso) {
        return permisoDao.save(permiso);
    }

    public void eliminar(Long id) {
        permisoDao.deleteById(id);
    }

    public Permiso obtenerPorNombre(String nombre) {
        return permisoDao.findByNombre(nombre);
    }


}
