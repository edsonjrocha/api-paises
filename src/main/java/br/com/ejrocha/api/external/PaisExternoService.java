package br.com.ejrocha.api.external;

import br.com.ejrocha.api.dto.PaisDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface PaisExternoService {

    List<PaisDTO> buscarTodosPaisesExternos();
    PaisDTO buscarPaisPorCodigoIso(String codigoIso);

}
