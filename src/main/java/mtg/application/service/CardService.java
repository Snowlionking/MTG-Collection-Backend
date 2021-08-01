package mtg.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.magicthegathering.javasdk.api.CardAPI;
import io.magicthegathering.javasdk.resource.Card;
import lombok.extern.slf4j.Slf4j;
import mtg.application.repository.CardRepository;
import mtg.application.service.converter.CardConverter;

@Service
@Slf4j
public class CardService {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardConverter cardConverter;

    public mtg.application.entity.Card addCardToDatabase(int multiverseId) {

        Card card = CardAPI.getCard(multiverseId);

        log.info("Cardname: " + card.getName() + " Set: " + card.getSetName());

        return cardRepository.save(cardConverter.convert(card));
    }

}
