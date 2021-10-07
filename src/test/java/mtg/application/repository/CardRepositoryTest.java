package mtg.application.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mtg.application.entity.CardTypes;
import mtg.application.entity.OwnedCard;

@SpringBootTest
public class CardRepositoryTest {

    @Autowired
    private CardRepository cardRepository;

    @BeforeEach
    public void setUp() {
        OwnedCard card = new OwnedCard();
        CardTypes cardTypes = new CardTypes();
        cardTypes.setTypes(createTypes());
        cardTypes.setSubTypes(createSubTypes());
        cardTypes.setSuperTypes(createSuperTypes());
        card.setCardName("Scion of the Ur-Dragon");
        card.setCardTypes(cardTypes);
        card.setCardEffect("Flying\r\n"
            + "{2}: Search your library for a Dragon permanent card and put it into your graveyard. If you do, Scion of the Ur-Dragon becomes a copy of that card until end of turn. Then shuffle your library.");
        card.setFlavourText("\"I am the blood of the ur-dragon, coursing through all dragonkind.\"");
        card.setPower("4");
        card.setToughness("4");
        card.setConvertedManaCost(5);
        card.setManaCost("{W}{U}{B}{R}{G}");
        cardRepository.save(card);
        assertThat(card.getId()).isNotNull();
    }

    @Test
    public void test() {

    }

    private List<String> createSubTypes() {
        List<String> subTypes = new ArrayList<>();
        subTypes.add("Dragon");
        subTypes.add("Avatar");
        return subTypes;
    }

    private List<String> createSuperTypes() {
        List<String> superTypes = new ArrayList<>();
        superTypes.add("Legendary");
        return superTypes;
    }

    private List<String> createTypes() {
        List<String> types = new ArrayList<>();
        types.add("Creature");
        return types;
    }

}
