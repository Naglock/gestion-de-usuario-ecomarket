package cl.ecomarket.usermanagement.service;

import cl.ecomarket.model.usuario;
import cl.ecomarket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class usuarioService {
    @Autowire
    private UserRepository userRepository;

    public List<usuario> listarUsuarios() {
        return userRepository.findAll();

    }

    public Optional<usuario> obtenerUsuarioPorId(Long id){
        return userRepository.findById(id);
    }

    public usuario guardarUsuario(usuario usuario) {
        return userRepository.save(usuario);
    }

    public usuario actualizarUsuario(usuario usuario) {
        return userRepository.save(usuario);
    }

    public void eliminarUsuario(Long id){
        userRepository.deleteById(id);
    }



}
