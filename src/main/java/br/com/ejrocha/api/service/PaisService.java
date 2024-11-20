package br.com.ejrocha.api.service;

import br.com.ejrocha.api.dto.PaisDTO;
import br.com.ejrocha.api.entity.Pais;
import br.com.ejrocha.api.repository.PaisRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PaisService {

    List<PaisDTO> listarTodos();
    List<PaisDTO> listarPorPopulacao();
    List<PaisDTO> listarPorArea();
    PaisDTO buscarPorId(Long id);
    PaisDTO buscarPorCodigoIso(String codigoIso);
    PaisDTO salvar(PaisDTO paisDTO);
    PaisDTO atualizar(Long id, PaisDTO paisDTO);
    void deletar(Long id);

    // Métodos para integração com API externa
    List<PaisDTO> listarPaisesExternos();
    PaisDTO buscarPaisExternoPorCodigoIso(String codigoIso);

    public int sincronizarPaisesExternos();

}
