package com.example.twitter.controller;

import com.example.twitter.service.TextAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/analizar/")
public class AnalysisController {

    @Autowired
    private TextAnalysisService analysisService;

    @PostMapping("/")
    public Map<String, Object> analizarTexto(@RequestBody Map<String, String> body) {
        System.out.println("Request body recibido: " + body);
        String texto = body.get("texto");
        return analysisService.analyzeAsMap(texto);
    }

}
