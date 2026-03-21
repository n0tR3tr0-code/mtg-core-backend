package com.mtg.mtg_core_backend.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mtg.mtg_core_backend.dto.CardResponseDto;
import com.mtg.mtg_core_backend.model.Card;
import com.mtg.mtg_core_backend.model.CardPrice;
import com.mtg.mtg_core_backend.repository.CardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScryfallService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final CardRepository cardRepository;

    @Value("${INGESTOR_SERVICE_URL:http://ingestor-python:5000}")
    private String ingestorUrl;

    public Card getCardData(String cardName) {
        Optional<Card> existingCard = cardRepository.findByCardName(cardName);

        if(existingCard.isPresent()) {
            System.out.println("Dati recuperati dal db per: " + cardName);
            return existingCard.get();
        }

        System.out.println("Carta non trovata nel DB. Chiamata all'ingestor per: " + cardName);
        CardResponseDto dto = fetchFromPython(cardName);

        return saveCardToDb(dto);
    }

    public CardResponseDto fetchFromPython(String cardName) {
        String url = ingestorUrl + "/price/" + cardName;

        try{
            return restTemplate.getForObject(url, CardResponseDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Errore durante la chiamata all'ingestor Python: " + e.getMessage());
        }
    }

    private Card saveCardToDb(CardResponseDto dto) {
        Card card = new Card();
        card.setCardName(dto.getCardName());
        card.setTotalPrints(dto.getTotalPrints());

        if(dto.getPrints() != null) {
            card.setPrints(dto.getPrints().stream().map(p -> {
                CardPrice price = new CardPrice();
                price.setSetName(p.getSetName());
                price.setSetCode(p.getSetCode());
                price.setReleasedAt(p.getReleasedAt());

                price.setPriceEur(p.getPrices() != null ? p.getPrices().get("eur") : null);
                price.setPriceUsd(p.getPrices() != null ? p.getPrices().get("usd") : null);

                return price;
            }).collect(Collectors.toList()));
        }

        return cardRepository.save(card);
    }
}
