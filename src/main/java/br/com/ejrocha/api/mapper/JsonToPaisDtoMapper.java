package br.com.ejrocha.api.mapper;

import br.com.ejrocha.api.dto.PaisDTO;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

@Component
public class JsonToPaisDtoMapper {

    public PaisDTO mapToPaisDTO(JsonNode countryNode) {
        PaisDTO paisDTO = new PaisDTO();
        paisDTO.setNome(countryNode.at("/translations/por/common").asText(null));  // Acessa "name.common"
        paisDTO.setCodigoIso(countryNode.get("cca2").asText(null));   // Acessa "cca2"
        paisDTO.setCodigoIso3(countryNode.get("cca3").asText(null));
        paisDTO.setRegiao(countryNode.get("region").asText(null));
        if(countryNode.get("subregion") != null) {
            paisDTO.setSubRegiao(countryNode.get("subregion").asText(null));
        }
        if(countryNode.get("area") != null) {
            paisDTO.setArea(countryNode.get("area").asLong(0));
        }
        if(countryNode.get("population") != null) {
            paisDTO.setPopulacao(countryNode.get("population").asLong(0));
        }

        if(countryNode.get("fifa") != null) {
            paisDTO.setCodigoFifa(countryNode.get("fifa").asText(null));
        }

        if(countryNode.at("/flags/svg") != null) {
            paisDTO.setBandeiraSvg(countryNode.at("/flags/svg").asText(null));
        }

        return paisDTO;
    }


}
