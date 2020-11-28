package mtg.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mtg.application.entity.Card;
import mtg.application.entity.Deck;
import mtg.application.entity.Status;
import mtg.application.repository.CardRepository;
import mtg.application.repository.DeckRepository;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    public CommandLineRunner initDatabase(CardRepository cardRepository, DeckRepository deckRepository) {
        Deck rograkh = new Deck("Rograkh, Son of Rohgahh", "Strength is relative", Status.WIP);
        return args -> {
            deckRepository.save(rograkh);
            deckRepository.save(new Deck("Brago, King Eternal", "My rule persists beyond death itself", Status.READY));

            cardRepository.save(new Card("Sol Ring", "Artifact"));
            cardRepository.save(new Card("Arcane Signet", "Artifact"));

            cardRepository.findAll().forEach(card -> log.info("Preloaded " + card));

            deckRepository.findAll().forEach(deck -> log.info("Preloaded " + deck));
        };
    }

}
