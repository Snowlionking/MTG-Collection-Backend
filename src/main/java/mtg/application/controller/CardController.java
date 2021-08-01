package mtg.application.controller;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.magicthegathering.javasdk.api.CardAPI;
import mtg.application.model.CardView;
import mtg.application.service.CardService;
import mtg.application.service.converter.CardConverter;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CardController {

    @Inject
    private CardService cardService;
    @Inject
    CardConverter cardConverter;

    @GetMapping("/card/{name}")
    public List<CardView> findCardByName(@PathVariable("name") String name) {
        System.out.println("Calling: " + name);
        return cardConverter.convert(CardAPI.getAllCards(Arrays.asList("name=" + name)));
    }

    @PostMapping("/card/{multiverseId}")
    public mtg.application.entity.Card addCardToDataBase(@PathVariable String multiverseId) {
        return cardService.addCardToDatabase(Integer.parseInt(multiverseId));
    }

}
