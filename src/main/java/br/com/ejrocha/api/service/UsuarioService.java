package br.com.ejrocha.api.service;

import br.com.ejrocha.api.dto.UsuarioDTO;
import br.com.ejrocha.api.entity.Usuario;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioService {

    UsuarioDTO cadastrar(UsuarioDTO usuarioDTO);

    boolean existeUsuario(UsuarioDTO usuarioDTO);

    UserDetails retornarPorLogin(String login);

}
