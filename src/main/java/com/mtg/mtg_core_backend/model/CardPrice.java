package com.mtg.mtg_core_backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "card_prices")
@Data
public class CardPrice {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String setName;
    private String setCode;

    private String priceEur;
    private String priceUsd;

    private String releasedAt;
    
    @ManyToOne
    @JoinColumn(name = "card_id")
    @JsonBackReference
    private Card card;
}
