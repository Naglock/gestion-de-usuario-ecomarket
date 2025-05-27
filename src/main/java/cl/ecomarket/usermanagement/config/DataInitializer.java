package cl.ecomarket.usermanagement.config;

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
            
            Permiso adminPermiso = permisoRepository.findByNombre("ROLE_ADMINISTRADOR_SISTEMA")
                .orElseGet(() -> permisoRepository.save(new Permiso(null, "ROLE_ADMINISTRADOR_SISTEMA")));

            Permiso vendedorPermiso = permisoRepository.findByNombre("ROLE_EMPLEADO_VENTAS")
                .orElseGet(() -> permisoRepository.save(new Permiso(null, "ROLE_EMPLEADO_VENTAS")));

            Permiso clientePermiso = permisoRepository.findByNombre("ROLE_CLIENTE")
                .orElseGet(() -> permisoRepository.save(new Permiso(null, "ROLE_CLIENTE")));
            
            Permiso gerentePermiso = permisoRepository.findByNombre("ROLE_GERENTE_TIENDA")
                .orElseGet(() -> permisoRepository.save(new Permiso(null, "ROLE_GERENTE_TIENDA")));

            Permiso logisticaPermiso = permisoRepository.findByNombre("ROLE_LOGISTICA")
                .orElseGet(() -> permisoRepository.save(new Permiso(null, "ROLE_LOGISTICA")));
            
            if (usuarioRepository.findByUsername("Administrador").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setUsername("Administrador");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setPermisos(Set.of(adminPermiso));
                usuarioRepository.save(admin);
            }

        };
    }
}

