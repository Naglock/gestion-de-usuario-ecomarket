package cl.ecomarket.usermanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.ecomarket.usermanagement.dao.UsuarioDao;
import cl.ecomarket.usermanagement.model.Usuario;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioDao usuarioDao;

    public List<Usuario> listarTodos() {
        return usuarioDao.findAll();
    }

    public Usuario obtenerPorId(Long id) {
        return usuarioDao.findById(id).orElse(null);
    }

    public Usuario guardad(Usuario usuario) {
        return usuarioDao.save(usuario);
    }

    public void eliminar(Long id) {
        usuarioDao.deleteById(id);
    }

    



}
