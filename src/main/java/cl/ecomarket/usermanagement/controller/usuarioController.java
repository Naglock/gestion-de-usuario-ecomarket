package cl.ecomarket.usermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.ecomarket.usermanagement.model.Usuario;
import cl.ecomarket.usermanagement.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioService servicio;
    
    @GetMapping
    public List<Usuario> listar(){
        return servicio.listarTodos();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id) {
        Usuario usuario = servicio.obtenerPorId(id);
        if (usuario != null){
            return ResponseEntity.ok(usuario);
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Usuario crear(@RequestBody Usuario usuario){
        return servicio.guardar(usuario);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @RequestBody Usuario nuevoUsuario){
        Usuario existente = servicio.obtenerPorId(id);
        if (existente != null){
            existente.setUsername(nuevoUsuario.getUsername());
            existente.setPassword(nuevoUsuario.getPassword());
            servicio.guardar(existente);
            return ResponseEntity.ok(existente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (servicio.obtenerPorId(id) != null){
            servicio.eliminar(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
