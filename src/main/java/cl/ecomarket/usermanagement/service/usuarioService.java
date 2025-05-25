package cl.ecomarket.usermanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.ecomarket.usermanagement.dao.UsuarioDao;
import cl.ecomarket.usermanagement.model.Usuario;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> listarTodos() {
        return usuarioDao.findAll();
    }

    public Usuario obtenerPorId(Long id) {
        return usuarioDao.findById(id).orElse(null);
    }

    public Usuario guardar(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioDao.save(usuario);
    }

    public void eliminar(Long id) {
        usuarioDao.deleteById(id);
    }

    public Usuario obtenerPorNombreUsuario(String username) {
        return usuarioDao.findByUsername(username).orElse(null);
    }
}