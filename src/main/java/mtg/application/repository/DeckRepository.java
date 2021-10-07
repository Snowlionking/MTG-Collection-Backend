package mtg.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mtg.application.entity.Deck;

public interface DeckRepository extends JpaRepository<Deck, Long> {

}
