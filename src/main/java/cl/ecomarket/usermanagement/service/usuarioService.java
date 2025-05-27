package cl.ecomarket.usermanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.ecomarket.usermanagement.model.Usuario;
import cl.ecomarket.usermanagement.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario guardar(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario obtenerPorNombreUsuario(String username) {
        return usuarioRepository.findByUsername(username).orElse(null);
    }

    public Usuario actualizarUsuario(Usuario usuarioActualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(usuarioActualizado.getId())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuarioExistente.setUsername(usuarioActualizado.getUsername());
        usuarioExistente.setEmail(usuarioActualizado.getEmail());
        usuarioExistente.setPermisos(usuarioActualizado.getPermisos());
        return usuarioRepository.save(usuarioExistente);
    }

    public List<Usuario> listarPorRol(String nombreRol) {
        return usuarioRepository.findAll().stream()
            .filter(u -> u.getPermisos().stream()
                .anyMatch(p -> p.getNombre().equals(nombreRol)))
            .toList();
    }

    public boolean existeUsuario(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    public ResponseEntity<Long> obtenerIdUsuario(String username) {
        return usuarioRepository.findByUsername(username)
                .map(usuario -> ResponseEntity.ok(usuario.getId()))
                .orElse(ResponseEntity.notFound().build());
    }
}