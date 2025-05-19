package com.example.twitter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.nio.charset.StandardCharsets;

@Service
public class TextAnalysisService {

    private static final Logger logger = LoggerFactory.getLogger(TextAnalysisService.class);

    public Map<String, Object> analyzeAsMap(String text) {
        try {
            // Modificar el proceso para pasar el texto como argumento
            ProcessBuilder builder = new ProcessBuilder("python3", "main.py", text);
            builder.redirectErrorStream(true);

            Process process = builder.start();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                // Esperar a que termine el proceso
                process.waitFor();

                String resultStr = result.toString().trim();
                if (resultStr.isEmpty() || !resultStr.startsWith("{")) {
                    logger.warn("Resultado inesperado: " + resultStr);
                    return Map.of("label", "error", "confidence", 0.0);
                }

                // Convertir la salida a un mapa utilizando Jackson
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> response = mapper.readValue(resultStr, Map.class);

                return response;
            } catch (IOException | InterruptedException e) {
                logger.error("Error al procesar el texto: ", e);
                return Map.of("label", "error", "confidence", 0.0);
            }
        } catch (Exception e) {
            logger.error("Error al ejecutar el proceso Python: ", e);
            return Map.of("label", "error", "confidence", 0.0);
        }
    }
}