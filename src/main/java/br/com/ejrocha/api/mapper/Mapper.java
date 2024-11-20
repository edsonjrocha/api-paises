package br.com.ejrocha.api.mapper;

import br.com.ejrocha.api.dto.PaisDTO;
import br.com.ejrocha.api.dto.UsuarioDTO;
import br.com.ejrocha.api.entity.Pais;
import br.com.ejrocha.api.entity.Usuario;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {

    Mapper INSTANCE = Mappers.getMapper(Mapper.class);

    PaisDTO paisToDTO(Pais pais);
    Pais dtoToPais(PaisDTO dto);

    @Mapping(source = "permissoes", target = "permissao")
    UsuarioDTO usuarioToDTO(Usuario usuario);

    @Mapping(source = "permissao", target = "permissoes")
    Usuario dtoToUsuario(UsuarioDTO dto);

}
