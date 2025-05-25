package cl.ecomarket.usermanagement.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import cl.ecomarket.usermanagement.model.Permiso;
import cl.ecomarket.usermanagement.model.Usuario;
import cl.ecomarket.usermanagement.service.PermisoService;
import cl.ecomarket.usermanagement.service.UsuarioService;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private PermisoService permisoService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    public List<Usuario> listar(){
        return usuarioService.listarTodos();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        if (usuario != null){
            return ResponseEntity.ok(usuario);
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Usuario crear(@RequestBody Usuario usuario){
        return usuarioService.guardar(usuario);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @RequestBody Usuario nuevoUsuario){
        Usuario existente = usuarioService.obtenerPorId(id);
        if (existente != null){
            existente.setUsername(nuevoUsuario.getUsername());
            existente.setPassword(nuevoUsuario.getPassword());
            usuarioService.guardar(existente);
            return ResponseEntity.ok(existente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (usuarioService.obtenerPorId(id) != null){
            usuarioService.eliminar(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

        @PutMapping("/{id}/permisos")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<?> asignarPermisos(@PathVariable Long id,@RequestBody Set<String> nombresPermisos) {

            Usuario usuario = usuarioService.obtenerPorId(id);
            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }

            Set<Permiso> permisos = nombresPermisos.stream()
                    .map(nombre -> permisoService.obtenerPorNombre(nombre).orElse(null))
                    .filter(p -> p != null)
                    .collect(Collectors.toSet());

            usuario.setPermisos(permisos);
            usuarioService.actualizarUsuario(usuario);

            return ResponseEntity.ok("Permisos actualizados");
        }

    @GetMapping("/whoami")
    public String whoami(Authentication authentication) {
        return "Usuario: " + authentication.getName() + ", Roles: " + authentication.getAuthorities();
    }


}
