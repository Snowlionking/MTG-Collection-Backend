package mtg.application.service.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import io.magicthegathering.javasdk.resource.Card;
import mtg.application.entity.CardTypes;
import mtg.application.model.CardView;

@Service
public class CardConverter {

    public mtg.application.entity.Card convert(Card card) {
        List<String> subTypes = new ArrayList<>();
        if (null != card.getSubtypes()) {
            subTypes = Arrays.asList(card.getSubtypes());
        }
        List<String> superTypes = new ArrayList<>();
        if (null != card.getSupertypes()) {
            superTypes = Arrays.asList(card.getSupertypes());
        }
        List<String> types = new ArrayList<>();
        if (null != card.getTypes()) {
            types = Arrays.asList(card.getTypes());
        }
        return mtg.application.entity.Card.builder()
            .cardEffect(card.getText())
            .cardName(card.getName())
            .cardTypes(CardTypes.builder().subTypes(subTypes).superTypes(superTypes).types(types).build())
            .convertedManaCost(card.getCmc())
            .flavourText(card.getFlavor())
            .imageUrl(card.getImageUrl())
            .manaCost(card.getManaCost())
            .numberOwn(1)
            .multiverseId(card.getMultiverseid())
            .build();
    }

    public List<CardView> convert(List<Card> allCards) {
        if (allCards == null) {
            return null;
        }

        List<CardView> cardViewList = new ArrayList<>();

        for (Card card : allCards) {
            if (card.getMultiverseid() == -1) {
                continue;
            }
            CardView cardView = new CardView(card.getName(), card.getImageUrl(), card.getMultiverseid());
            cardViewList.add(cardView);
        }

        return cardViewList;
    }

}
