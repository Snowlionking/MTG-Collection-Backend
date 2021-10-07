package mtg.application.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.magicthegathering.javasdk.api.CardAPI;
import io.magicthegathering.javasdk.resource.Card;
import lombok.extern.slf4j.Slf4j;
import mtg.application.entity.OwnedCard;
import mtg.application.model.CardViewList;
import mtg.application.model.ScryfallCard;
import mtg.application.repository.CardRepository;
import mtg.application.service.converter.CardConverter;

@Service
@Slf4j
public class CardService {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardConverter cardConverter;

    private RestTemplate restTemplate = new RestTemplate();

    public OwnedCard addCardToDatabase(int multiverseId) {

        OwnedCard ownedCard = cardRepository.findByMultiverseId(multiverseId);

        if (ownedCard == null) {
            Card card = CardAPI.getCard(multiverseId);

            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

            ScryfallCard cardScryfall = restTemplate
                .getForObject("https://api.scryfall.com/cards/multiverse/" + multiverseId, ScryfallCard.class);

            ownedCard = cardRepository.save(cardConverter.convert(card, cardScryfall));

            log.info("Card was added to DB.");

            return ownedCard;
        }

        log.info("Card already existed. No new Card was added.");
        return ownedCard;
    }

    public CardViewList findCards(String name) {
        List<Card> allCards = CardAPI.getAllCards(Arrays.asList("name=" + name));
        log.info("Following cards were found: " + allCards);
        return cardConverter.convert(allCards);
    }

    public CardViewList getAll() {
        CardViewList cardViewList = cardConverter.convertFromDBToView(cardRepository.findAll());
        log.info("Following cards were found in the DB: " + cardViewList);
        return cardViewList;
    }

}
