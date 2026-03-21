package com.mtg.mtg_core_backend.controller;

import org.springframework.web.bind.annotation.*;
import com.mtg.mtg_core_backend.dto.CardResponseDto;
import com.mtg.mtg_core_backend.service.ScryfallService;

@RestController
@RequestMapping("/api/cards")
public class CardController {
    private final ScryfallService scryfallService;

    public CardController(ScryfallService scryfallService) {
        this.scryfallService = scryfallService;
    }

    @GetMapping("/search/{cardName}")
    public CardResponseDto searchCards(@PathVariable String cardName) {
        return scryfallService.fetchPrices(cardName);
    }
}
