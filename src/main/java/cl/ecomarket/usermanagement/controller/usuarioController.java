package cl.ecomarket.usermanagement.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.ecomarket.usermanagement.model.Permiso;
import cl.ecomarket.usermanagement.model.Usuario;
import cl.ecomarket.usermanagement.service.PermisoService;
import cl.ecomarket.usermanagement.service.UsuarioService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    @Autowired
    private PermisoService permisoService;
    
    @Autowired
    private UsuarioService usuarioService;

    private boolean tieneRol(Authentication auth, String rol) {
    return auth.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .anyMatch(r -> r.equals(rol));
}
    
    @GetMapping
    public List<Usuario> listar(Authentication authentication) {
        boolean isAdmin = tieneRol(authentication, "ROLE_ADMINISTRADOR_SISTEMA");
        boolean isVendedor = tieneRol(authentication, "ROLE_EMPLEADO_VENTAS");

        if (isAdmin) {
            return usuarioService.listarTodos();
        } else if (isVendedor) {
            return usuarioService.listarPorRol("ROLE_CLIENTE");
        }

        return List.of();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id, Authentication auth) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        if (usuario == null) return ResponseEntity.notFound().build();

        if (tieneRol(auth, "ROLE_ADMINISTRADOR_SISTEMA")) {
            return ResponseEntity.ok(usuario);
        }

        if (tieneRol(auth, "ROLE_EMPLEADO_VENTAS")) {
            boolean esCliente = usuario.getPermisos().stream()
                .anyMatch(p -> p.getNombre().equals("ROLE_CLIENTE"));
            return esCliente ? ResponseEntity.ok(usuario) : ResponseEntity.status(403).build();
        }

        // Si el cliente está viendo su propio perfil
        if (tieneRol(auth, "ROLE_CLIENTE") && auth.getName().equals(usuario.getUsername())) {
            return ResponseEntity.ok(usuario);
        }

        return ResponseEntity.status(403).build();
    }


    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLEADO_VENTAS', 'ADMINISTRADOR_SISTEMA')")
    public Usuario crear(@RequestBody Usuario usuario, Authentication authentication) {
        boolean isVendedor = tieneRol(authentication, "ROLE_EMPLEADO_VENTAS");

        if (isVendedor) {
            // Forzar rol CLIENTE
            Permiso rolCliente = permisoService.obtenerPorNombre("ROLE_CLIENTE")
                .orElseThrow(() -> new RuntimeException("Permiso ROLE_CLIENTE no existe"));

            usuario.setPermisos(Set.of(rolCliente));
        }

        return usuarioService.guardar(usuario);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR_SISTEMA')")
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
    @PreAuthorize("hasRole('ADMINISTRADOR_SISTEMA')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (usuarioService.obtenerPorId(id) != null){
            usuarioService.eliminar(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/permisos")
    @PreAuthorize("hasRole('ADMINISTRADOR_SISTEMA')")
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
    @PutMapping("/{id}/permisos/remover")
    @PreAuthorize("hasRole('ADMINISTRADOR_SISTEMA')")
    public ResponseEntity<?> removerPermisos(@PathVariable Long id, @RequestBody Set<String> nombresPermisos) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        Set<Permiso> permisosARemover = nombresPermisos.stream()
            .map(nombre -> permisoService.obtenerPorNombre(nombre).orElse(null))
            .filter(p -> p != null)
            .collect(Collectors.toSet());

        usuario.getPermisos().removeAll(permisosARemover);

        usuarioService.actualizarUsuario(usuario);

        return ResponseEntity.ok("Permisos eliminados");
    }
    
    @GetMapping("/existe/{username}")
    public boolean existeUsuario(@PathVariable String username) {
        return usuarioService.existeUsuario(username);
    }

    @GetMapping("/id-por-username/{username}")
    public ResponseEntity<Long> obtenerIdPorUsername(@PathVariable String username) {
        return usuarioService.obtenerIdUsuario(username);
    }

}
