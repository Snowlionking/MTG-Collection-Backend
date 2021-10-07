package mtg.application.service.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import mtg.application.entity.Deck;
import mtg.application.model.DeckViewList;

@Service
public class DeckConverter {

    public DeckViewList convert(List<Deck> findAll) {
        DeckViewList list = new DeckViewList();
        List<String> decknames = new ArrayList<>();
        for (Deck deck : findAll) {
            decknames.add(deck.getDeckName());
        }
        list.setDeckname(decknames);
        return list;
    }

}
