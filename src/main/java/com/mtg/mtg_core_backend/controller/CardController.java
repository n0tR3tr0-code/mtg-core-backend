package com.mtg.mtg_core_backend.controller;

import org.springframework.web.bind.annotation.*;
import com.mtg.mtg_core_backend.dto.CardResponseDto;
import com.mtg.mtg_core_backend.model.Card;
import com.mtg.mtg_core_backend.service.ScryfallService;

@RestController
@RequestMapping("/api/cards")
public class CardController {
    private final ScryfallService scryfallService;

    public CardController(ScryfallService scryfallService) {
        this.scryfallService = scryfallService;
    }

    @GetMapping("/search/{cardName}")
    public Card searchCards(@PathVariable String cardName) {
        return scryfallService.getCardData(cardName);
    }


}
