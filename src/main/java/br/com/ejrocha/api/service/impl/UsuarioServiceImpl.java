package br.com.ejrocha.api.service.impl;

import br.com.ejrocha.api.dto.UsuarioDTO;
import br.com.ejrocha.api.entity.Usuario;
import br.com.ejrocha.api.mapper.Mapper;
import br.com.ejrocha.api.repository.UsuarioRepository;
import br.com.ejrocha.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UsuarioDTO cadastrar(UsuarioDTO usuarioDTO) {
        String senhaEncriptada = new BCryptPasswordEncoder().encode(usuarioDTO.getSenha());
        usuarioDTO.setSenha(senhaEncriptada);
        Usuario usuario = Mapper.INSTANCE.dtoToUsuario(usuarioDTO);
        return Mapper.INSTANCE.usuarioToDTO(usuarioRepository.save(usuario));
    }

    @Override
    public UserDetails retornarPorLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }

    @Override
    public boolean existeUsuario(UsuarioDTO usuarioDTO) {
        return usuarioRepository.findByLogin(usuarioDTO.getLogin()) != null;
    }
}
