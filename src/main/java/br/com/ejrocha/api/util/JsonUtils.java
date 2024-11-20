package br.com.ejrocha.api.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Método para converter a string JSON em JsonNode
    public static JsonNode parseJson(String json) throws IOException {
        return objectMapper.readTree(json);
    }

}
