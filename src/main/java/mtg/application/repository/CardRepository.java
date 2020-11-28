package mtg.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mtg.application.entity.Card;

public interface CardRepository extends JpaRepository<Card, Long> {

}
