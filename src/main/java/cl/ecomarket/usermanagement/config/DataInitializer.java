package cl.ecomarket.usermanagement.config;

import java.util.Optional;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import cl.ecomarket.usermanagement.model.Permiso;
import cl.ecomarket.usermanagement.model.Usuario;
import cl.ecomarket.usermanagement.repository.PermisoRepository;
import cl.ecomarket.usermanagement.repository.UsuarioRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(UsuarioRepository usuarioRepository, PermisoRepository permisoRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            
            Permiso adminPermiso;
            Optional<Permiso> optionalAdmin = permisoRepository.findByNombre("ROLE_ADMIN");
            if (optionalAdmin.isPresent()) {
                adminPermiso = optionalAdmin.get();
            } else {
                adminPermiso = permisoRepository.save(new Permiso(null, "ROLE_ADMIN"));
            }

            
            Permiso vendedorPermiso;
            Optional<Permiso> optionalVendedor = permisoRepository.findByNombre("ROLE_VENDEDOR");
            if (optionalVendedor.isPresent()) {
                vendedorPermiso = optionalVendedor.get();
            } else {
                vendedorPermiso = permisoRepository.save(new Permiso(null, "ROLE_VENDEDOR"));
            }

            Permiso clientePermiso;
            Optional<Permiso> optionalCliente = permisoRepository.findByNombre("ROLE_CLIENTE");
            if (optionalCliente.isPresent()) {
                clientePermiso = optionalCliente.get();
            } else {
                clientePermiso = permisoRepository.save(new Permiso(null, "ROLE_CLIENTE"));
            }

            
            if (usuarioRepository.findByUsername("admin").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setUsername("admin"); 
                admin.setPassword(passwordEncoder.encode("admin123")); 
                admin.setPermisos(Set.of(adminPermiso)); 
                usuarioRepository.save(admin);
            }
        };
    }
}

