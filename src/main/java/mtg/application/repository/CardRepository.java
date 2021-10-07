package mtg.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mtg.application.entity.OwnedCard;

public interface CardRepository extends JpaRepository<OwnedCard, Long> {

    OwnedCard findByMultiverseId(int multiverseId);

}
