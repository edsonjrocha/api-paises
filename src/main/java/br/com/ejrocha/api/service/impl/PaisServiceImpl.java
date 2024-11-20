package br.com.ejrocha.api.service.impl;

import br.com.ejrocha.api.dto.PaisDTO;
import br.com.ejrocha.api.entity.Pais;
import br.com.ejrocha.api.exception.PaisDuplicadoException;
import br.com.ejrocha.api.external.PaisExternoService;
import br.com.ejrocha.api.mapper.Mapper;
import br.com.ejrocha.api.repository.PaisRepository;
import br.com.ejrocha.api.service.PaisService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaisServiceImpl implements PaisService {

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private PaisExternoService paisExternoService;
    @Autowired
    private Mapper mapper;

    @Override
    public List<PaisDTO> listarTodos() {
        List<Pais> paises = paisRepository.findAllByOrderByNomeAsc();
        return paises.stream().map(Mapper.INSTANCE::paisToDTO).toList();
    }

    @Override
    public List<PaisDTO> listarPorPopulacao() {
        List<Pais> paises = paisRepository.findAllByOrderByPopulacaoDesc();
        return paises.stream().map(Mapper.INSTANCE::paisToDTO).toList();
    }

    @Override
    public List<PaisDTO> listarPorArea() {
        List<Pais> paises = paisRepository.findAllByOrderByAreaDesc();
        return paises.stream().map(Mapper.INSTANCE::paisToDTO).toList();
    }

    @Override
    public PaisDTO buscarPorId(Long id) {
        Optional<Pais> pais = paisRepository.findById(id);
        return pais.map(Mapper.INSTANCE::paisToDTO).orElse(null);
    }

    @Override
    public PaisDTO buscarPorCodigoIso(String codigoIso) {
        Optional<Pais> pais = paisRepository.findByCodigoIsoIgnoreCase(codigoIso);
        return pais.map(Mapper.INSTANCE::paisToDTO).orElse(null);
    }

    @Override
    public PaisDTO salvar(PaisDTO paisDTO) {
        // Verifica se já existe um país com o mesmo código ISO
        Optional<Pais> paisExistente = paisRepository.findByCodigoIsoIgnoreCase(paisDTO.getCodigoIso());
        if (paisExistente.isPresent()) {
            throw new PaisDuplicadoException("Já existe um país com o código ISO: " + paisDTO.getCodigoIso());
        }
        paisDTO.setCodigoIso(paisDTO.getCodigoIso().toUpperCase());

        Pais pais = Mapper.INSTANCE.dtoToPais(paisDTO);

        return Mapper.INSTANCE.paisToDTO(paisRepository.save(pais));

    }

    @Override
    public PaisDTO atualizar(Long id, PaisDTO paisDTO) {
        Pais pais = paisRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("País não encontrado"));

        paisDTO.setId(id);
        Pais paisAtualizado = Mapper.INSTANCE.dtoToPais(paisDTO);

        return Mapper.INSTANCE.paisToDTO(paisRepository.save(paisAtualizado));
    }

    @Override
    public void deletar(Long id) {
        Pais pais = paisRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("País não encontrado"));
        paisRepository.delete(pais);
    }

    @Override
    public List<PaisDTO> listarPaisesExternos() {
        try {
            return paisExternoService.buscarTodosPaisesExternos();
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public PaisDTO buscarPaisExternoPorCodigoIso(String codigoIso) {
        return paisExternoService.buscarPaisPorCodigoIso(codigoIso);
    }

    @Override
    public int sincronizarPaisesExternos() {
        // Busca a lista de países externos
        List<PaisDTO> paisesExternos = paisExternoService.buscarTodosPaisesExternos();

        // Filtra e adiciona ao banco apenas os países novos
        List<Pais> novosPaises = paisesExternos.stream()
                .map(Mapper.INSTANCE::dtoToPais)
                .filter(this::isNovoPais)
                .collect(Collectors.toList());

        paisRepository.saveAll(novosPaises);
        return novosPaises.size();
    }

    private boolean isNovoPais(Pais pais) {
        PaisDTO paisDTO = this.buscarPorCodigoIso(pais.getCodigoIso());
        return paisDTO == null ;
    }

}
