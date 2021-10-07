package mtg.application.controller;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import mtg.application.entity.OwnedCard;
import mtg.application.model.CardViewList;
import mtg.application.model.DeckViewList;
import mtg.application.service.CardService;
import mtg.application.service.DeckService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Slf4j
public class CardController {

    @Inject
    private CardService cardService;
    @Inject
    private DeckService deckService;

    @GetMapping("/card/{name}")
    public CardViewList searchGathererByCardname(@PathVariable("name") String name) {
        log.info("Searching " + name + "...");
        return cardService.findCards(name);
    }

    @GetMapping("/cards/owned")
    public CardViewList fetchAllCards() {
        log.info("Fetching all Cards owned...");
        return cardService.getAll();
    }

    @GetMapping("/decks/owned")
    public DeckViewList fetchAllDecks() {
        log.info("Fetching all Decks owned...");
        return deckService.getAll();
    }

    @PostMapping(path = "/card")
    public OwnedCard addCardToDataBase(@RequestBody Integer multiverseId) {
        log.info("Adding " + multiverseId + " to DB...");
        return cardService.addCardToDatabase(multiverseId);
    }

}
