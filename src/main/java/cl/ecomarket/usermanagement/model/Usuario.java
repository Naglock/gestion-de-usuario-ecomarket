package cl.ecomarket.usermanagement.model;

import lombok.Data;
import lombok.NoArgsConstructor;


import jakarta.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Usuario {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuarios_permisos",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "permiso_id")
    )
    private Set<Permiso> roles;
}
