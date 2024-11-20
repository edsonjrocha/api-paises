package br.com.ejrocha.api.controller;

import br.com.ejrocha.api.dto.LoginDTO;
import br.com.ejrocha.api.dto.PaisDTO;
import br.com.ejrocha.api.dto.UsuarioDTO;
import br.com.ejrocha.api.entity.Usuario;
import br.com.ejrocha.api.security.TokenService;
import br.com.ejrocha.api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginDTO loginDTO){

        UsernamePasswordAuthenticationToken userNamePassword = new UsernamePasswordAuthenticationToken(loginDTO.getLogin(), loginDTO.getSenha());
        Authentication auth = authenticationManager.authenticate(userNamePassword);

        String token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(token);

    }

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO) throws Exception {

        if(usuarioService.existeUsuario(usuarioDTO)) {
            throw new Exception("Usuario já existe");
        }

        UsuarioDTO novoUsuario = usuarioService.cadastrar(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);

    }

}
