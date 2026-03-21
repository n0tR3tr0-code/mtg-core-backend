// Questo DTO è molto importante perché quando Java chiama l'endpoint Python di Scryfall, riceverà una stringa di testo JSON
// Spring userà una libreria Jackson per trasformare automaticamente il testo in un oggetto Java CardResponseDto

package com.mtg.mtg_core_backend.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardResponseDto {
    @JsonProperty("card_name")
    private String cardName;

    @JsonProperty("total_prints")
    private int totalPrints;
    
    private List<CardPrintDTO> prints;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CardPrintDTO {

        @JsonProperty("set_name")
        private String setName;

        @JsonProperty("set_code")
        private String setCode;
        
        private Map<String, String> prices;

        @JsonProperty("released_at")
        private String releasedAt;

        private String uri;
    }
}
