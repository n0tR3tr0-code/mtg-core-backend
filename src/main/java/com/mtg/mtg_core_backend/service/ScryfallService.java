package com.mtg.mtg_core_backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mtg.mtg_core_backend.dto.CardResponseDto;

@Service
public class ScryfallService {
    private final RestTemplate restTemplate;

    @Value("${INGESTOR_SERVICE_URL:http://ingestor-python:5000}")
    private String ingestorUrl;

    public ScryfallService() {
        this.restTemplate = new RestTemplate();
    }

    public CardResponseDto fetchPrices(String cardName) {
        String url = ingestorUrl + "/price/" + cardName;

        try{
            return restTemplate.getForObject(url, CardResponseDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Errore durante la chiamata all'ingestor Python: " + e.getMessage());
        }
    }
}
