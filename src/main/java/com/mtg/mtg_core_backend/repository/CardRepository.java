package com.mtg.mtg_core_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mtg.mtg_core_backend.model.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByCardName(String cardName);
}