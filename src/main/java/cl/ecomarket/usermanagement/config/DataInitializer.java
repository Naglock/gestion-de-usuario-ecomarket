package cl.ecomarket.usermanagement.config;

import java.util.Optional;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import cl.ecomarket.usermanagement.dao.PermisoDao;
import cl.ecomarket.usermanagement.dao.UsuarioDao;
import cl.ecomarket.usermanagement.model.Permiso;
import cl.ecomarket.usermanagement.model.Usuario;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(UsuarioDao usuarioDao, PermisoDao permisoDao, PasswordEncoder passwordEncoder) {
        return args -> {
            // 💡 Aseguramos la existencia del permiso ROLE_ADMIN
            Permiso adminPermiso;
            Optional<Permiso> optionalAdmin = permisoDao.findByNombre("ROLE_ADMIN");
            if (optionalAdmin.isPresent()) {
                adminPermiso = optionalAdmin.get();
            } else {
                adminPermiso = permisoDao.save(new Permiso(null, "ROLE_ADMIN"));
            }

            // 💡 Aseguramos la existencia del permiso ROLE_VENDEDOR
            Permiso vendedorPermiso;
            Optional<Permiso> optionalVendedor = permisoDao.findByNombre("ROLE_VENDEDOR");
            if (optionalVendedor.isPresent()) {
                vendedorPermiso = optionalVendedor.get();
            } else {
                vendedorPermiso = permisoDao.save(new Permiso(null, "ROLE_VENDEDOR"));
            }

            // 💡 Creamos el usuario admin solo si no existe
            if (usuarioDao.findByUsername("admin").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setUsername("admin"); // asegúrate que en tu entidad se llame "nombre"
                admin.setPassword(passwordEncoder.encode("admin123")); // encriptar contraseña
                admin.setPermisos(Set.of(adminPermiso, vendedorPermiso)); // puedes asignar uno o varios permisos
                usuarioDao.save(admin);
            }
        };
    }
}

