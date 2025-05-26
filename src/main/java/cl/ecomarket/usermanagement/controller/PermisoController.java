package cl.ecomarket.usermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.ecomarket.usermanagement.model.Permiso;
import cl.ecomarket.usermanagement.service.PermisoService;

@RestController
@RequestMapping("/api/permisos")
public class PermisoController {
    
    @Autowired
    private PermisoService permisoService;

    @GetMapping
    public List<Permiso> listar() {
        return permisoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Permiso> obtenerPorId(@PathVariable Long id) {
        Permiso permiso = permisoService.obtenerPorId(id);
        if (permiso != null){
            return ResponseEntity.ok(permiso);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Permiso crear(@RequestBody Permiso permiso){
        return permisoService.guardarPermiso(permiso);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Permiso> actualizar(@PathVariable Long id, @RequestBody Permiso nuevoPermiso) {
        Permiso existente = permisoService.obtenerPorId(id);
        if (existente != null) {
            existente.setNombre(nuevoPermiso.getNombre());
            permisoService.guardarPermiso(existente);
            return ResponseEntity.ok(existente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> elimnar(@PathVariable Long id) {
        if (permisoService.obtenerPorId(id) != null) {
            permisoService.eliminar(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
