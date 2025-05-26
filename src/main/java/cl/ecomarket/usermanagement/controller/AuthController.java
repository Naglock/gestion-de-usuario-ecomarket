package cl.ecomarket.usermanagement.controller;

import cl.ecomarket.usermanagement.dto.ClienteRegisterDTO;
import cl.ecomarket.usermanagement.model.Permiso;
import cl.ecomarket.usermanagement.model.Usuario;
import cl.ecomarket.usermanagement.security.JwtUtil;
import cl.ecomarket.usermanagement.service.PermisoService;
import cl.ecomarket.usermanagement.service.UsuarioService;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
        String token = jwtUtil.generarToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PermisoService permisoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registrarCliente(@RequestBody ClienteRegisterDTO dto) {
        if (usuarioService.obtenerPorNombreUsuario(dto.getUsername()) != null) {
            return ResponseEntity.badRequest().body("El nombre de usuario ya está en uso");
        }

        Permiso permisoCliente = permisoService.obtenerPorNombre("ROLE_CLIENTE")
            .orElseThrow(() -> new RuntimeException("Permiso ROLE_CLIENTE no existe"));

        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setEmail(dto.getEmail());
        usuario.setPermisos(Set.of(permisoCliente));

        Usuario guardado = usuarioService.guardar(usuario);

        return ResponseEntity.ok("Cliente registrado exitosamente con ID: " + guardado.getId());
    }

    public record AuthRequest(String username, String password) {}
    public record AuthResponse(String token) {}
}
