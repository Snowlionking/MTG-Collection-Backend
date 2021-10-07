package mtg.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mtg.application.model.DeckViewList;
import mtg.application.repository.DeckRepository;
import mtg.application.service.converter.DeckConverter;

@Service
@Slf4j
public class DeckService {

    @Autowired
    private DeckRepository deckRepository;
    @Autowired
    private DeckConverter deckConverter;

    public DeckViewList getAll() {
        DeckViewList deckViewList = deckConverter.convert(deckRepository.findAll());
        log.info("Following decks were found in the DB: " + deckViewList);
        return deckViewList;
    }

}
