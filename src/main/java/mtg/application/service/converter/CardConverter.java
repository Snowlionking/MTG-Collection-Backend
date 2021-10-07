package mtg.application.service.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import io.magicthegathering.javasdk.resource.Card;
import lombok.extern.slf4j.Slf4j;
import mtg.application.entity.CardTypes;
import mtg.application.entity.OwnedCard;
import mtg.application.model.CardView;
import mtg.application.model.CardViewList;
import mtg.application.model.ScryfallCard;

@Service
@Slf4j
public class CardConverter {

    public OwnedCard convert(Card card, ScryfallCard cardScryfall) {
        List<String> subTypes = new ArrayList<>();
        if (null != card.getSubtypes()) {
            subTypes = Arrays.asList(card.getSubtypes());
        }
        List<String> superTypes = new ArrayList<>();
        if (null != card.getSupertypes()) {
            superTypes = Arrays.asList(card.getSupertypes());
        }
        List<String> cardTypes = new ArrayList<>();
        if (null != card.getTypes()) {
            cardTypes = Arrays.asList(card.getTypes());
        }
        OwnedCard ownedCard = new OwnedCard();
        ownedCard.setCardEffect(card.getText());
        ownedCard.setCardName(card.getName());
        CardTypes types = new CardTypes();
        types.setSubTypes(subTypes);
        types.setSuperTypes(superTypes);
        types.setTypes(cardTypes);
        ownedCard.setCardTypes(types);
        ownedCard.setConvertedManaCost(card.getCmc());
        ownedCard.setFlavourText(card.getFlavor());
        ownedCard.setImageUrl(card.getImageUrl());
        ownedCard.setManaCost(card.getManaCost());
        ownedCard.setMultiverseId(card.getMultiverseid());
        ownedCard.setNumberOwn(1);
        // ownedCard.setPrice(Double.valueOf(cardScryfall.getPrices().getEur_foil()));
        // ownedCard.setPrice(Double.valueOf(cardScryfall.getPrices().getEur()));
        return ownedCard;
    }

    public CardViewList convert(List<Card> allCards) {
        if (allCards == null) {
            return null;
        }

        CardViewList cardViewList = new CardViewList();
        List<CardView> cardViews = new ArrayList<>();

        for (Card card : allCards) {
            if (card.getMultiverseid() == -1) {
                continue;
            }
            cardViews.add(CardView.builder()
                .name(card.getName())
                .imageUrl(card.getImageUrl())
                .multiverseId(card.getMultiverseid())
                .build());
        }
        cardViewList.setCardViews(cardViews);
        return cardViewList;
    }

    public CardViewList convertFromDBToView(List<OwnedCard> findAll) {
        CardViewList cardViewList = new CardViewList();
        List<CardView> cardviews = new ArrayList<>();
        for (OwnedCard card : findAll) {
            cardviews.add(CardView.builder()
                .imageUrl(card.getImageUrl())
                .multiverseId(card.getMultiverseId())
                .name(card.getCardName())
                .build());
        }
        cardViewList.setCardViews(cardviews);
        return cardViewList;
    }

}
