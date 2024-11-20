package br.com.ejrocha.api.external.impl;

import br.com.ejrocha.api.dto.PaisDTO;
import br.com.ejrocha.api.external.PaisExternoService;
import br.com.ejrocha.api.mapper.JsonToPaisDtoMapper;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import br.com.ejrocha.api.util.JsonUtils;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class PaisExternoServiceImpl implements PaisExternoService {

    @Autowired
    private JsonToPaisDtoMapper jsonToPaisDtoMapper;

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${api.restcountries.base-url}")
    private String apiUrl;

    @Override
    public List<PaisDTO> buscarTodosPaisesExternos() {
        String url = apiUrl + "/all?lang=pt";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        try {
            // Usa o método utilitário para converter o JSON
            JsonNode root = JsonUtils.parseJson(response.getBody());

            // Transformar os dados em PaisDTO
            return StreamSupport.stream(root.spliterator(), false)
                    .map(node -> {
                        return jsonToPaisDtoMapper.mapToPaisDTO(node);
                    })
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar resposta da API externa: " + e.getMessage(), e);
        }
    }

    @Override
    public PaisDTO buscarPaisPorCodigoIso(String codigoIso) {
        String url = apiUrl + "/alpha/" + codigoIso + "?lang=pt";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Verifica se houve resposta
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            try {
                // Usa o método utilitário para converter o JSON
                JsonNode root = JsonUtils.parseJson(response.getBody());

                // Como RestCountries retorna um array para alguns endpoints, verificamos se é array ou objeto
                JsonNode countryNode = root.isArray() ? root.get(0) : root;

                return jsonToPaisDtoMapper.mapToPaisDTO(countryNode);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao processar resposta da API externa: " + e.getMessage(), e);
            }
        } else {
            throw new EntityNotFoundException("País com código ISO " + codigoIso + " não encontrado na API externa.");
        }
    }

}
