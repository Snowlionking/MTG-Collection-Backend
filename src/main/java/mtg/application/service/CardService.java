package mtg.application.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.magicthegathering.javasdk.api.CardAPI;
import io.magicthegathering.javasdk.resource.Card;
import lombok.extern.slf4j.Slf4j;
import mtg.application.exception.NoMatchingCardFoundException;
import mtg.application.exception.TooManyMatchesException;
import mtg.application.repository.CardRepository;
import mtg.application.service.converter.CardConverter;

@Service
@Slf4j
public class CardService {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardConverter cardConverter;

    public mtg.application.entity.Card findAndAddCardToDatabase(String name)
        throws TooManyMatchesException, NoMatchingCardFoundException {

        List<Card> allCards = CardAPI.getAllCards(Arrays.asList("name=" + name));

        for (Card card : allCards) {
            log.info("Cardname: " + card.getName() + " Set: " + card.getSetName());
        }

        // TODO: filter the right card
        // if (allCards.size() > 1) {
        // // TODO: Present the User with all cards found and let him chose one to save
        // throw new TooManyMatchesException(
        // String.format("The given name %s results in too many cards found. Please use a more specific cardname.",
        // name));
        // }

        if (allCards.size() == 0) {
            throw new NoMatchingCardFoundException(
                String.format("The given name %s resulted in no cards found. Please check your input.", name));
        }

        return cardRepository.save(cardConverter.convert(allCards.get(0)));
    }

}
