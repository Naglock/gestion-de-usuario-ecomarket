package cl.ecomarket.usermanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.ecomarket.usermanagement.model.Permiso;
import cl.ecomarket.usermanagement.repository.PermisoRepository;

@Service
public class PermisoService {
    @Autowired
    private PermisoRepository permisoRepository;

    public List<Permiso> listarTodos() {
        return permisoRepository.findAll();
    }

    public Permiso obtenerPorId(Long id) {
        return permisoRepository.findById(id).orElse(null);
    }

    public Permiso guardarPermiso(Permiso permiso) {
        return permisoRepository.save(permiso);
    }

    public void eliminar(Long id) {
        permisoRepository.deleteById(id);
    }

    public Optional<Permiso> obtenerPorNombre(String nombre) {
        return permisoRepository.findByNombre(nombre);
    }


}
