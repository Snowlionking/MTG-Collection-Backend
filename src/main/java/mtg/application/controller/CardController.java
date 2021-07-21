package mtg.application.controller;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.magicthegathering.javasdk.api.CardAPI;
import io.magicthegathering.javasdk.resource.Card;
import mtg.application.exception.NoMatchingCardFoundException;
import mtg.application.exception.TooManyMatchesException;
import mtg.application.service.CardService;

@RestController
public class CardController {

    @Inject
    private CardService cardService;

    @GetMapping("/card/filter")
    public List<Card> findCardByFilter(@RequestParam(name = "filter") List<String> filter) {
        return CardAPI.getAllCards(filter);
    }

    @GetMapping("/card/{name}")
    public List<Card> findCardByName(@PathParam("name") String name) {
        return CardAPI.getAllCards(Arrays.asList("name=" + name));
    }

    @PostMapping("/card/{name}")
    public mtg.application.entity.Card addCardToDataBase(@PathVariable String name)
        throws TooManyMatchesException, NoMatchingCardFoundException {
        return cardService.findAndAddCardToDatabase(name);
    }

}
