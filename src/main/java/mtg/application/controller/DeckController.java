package mtg.application.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import mtg.application.entity.Deck;
import mtg.application.entity.Status;
import mtg.application.exception.DeckNotFoundException;
import mtg.application.repository.DeckRepository;

@RestController
public class DeckController {

    private final DeckRepository deckRepository;

    private final DeckModelAssembler deckModelAssembler;

    DeckController(DeckRepository deckRepository, DeckModelAssembler deckModelAssembler) {
        this.deckRepository = deckRepository;
        this.deckModelAssembler = deckModelAssembler;
    }

    @GetMapping("/decks")
    CollectionModel<EntityModel<Deck>> findAll() {
        List<EntityModel<Deck>> decks = deckRepository.findAll().stream().map(deckModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(decks, linkTo(methodOn(DeckController.class).findAll()).withSelfRel());
    }

    @GetMapping("/decks/{id}")
    EntityModel<Deck> findById(@PathVariable Long id) {
        Deck deck = deckRepository.findById(id).orElseThrow(() -> new DeckNotFoundException(id));

        return deckModelAssembler.toModel(deck);
    }

    @PostMapping("/decks")
    ResponseEntity<EntityModel<Deck>> newDeck(@RequestBody Deck deck) {
        deck.setStatus(Status.WIP);
        Deck newDeck = deckRepository.save(deck);
        return ResponseEntity.created(linkTo(methodOn(DeckController.class).findById(newDeck.getId())).toUri())
                .body(deckModelAssembler.toModel(newDeck));
    }

    @PutMapping("/orders/{id}/ready")
    ResponseEntity<?> ready(@PathVariable Long id) {

        Deck deck = deckRepository.findById(id) //
                .orElseThrow(() -> new DeckNotFoundException(id));

        if (deck.getStatus() == Status.WIP) {
            deck.setStatus(Status.READY);
            return ResponseEntity.ok(deckModelAssembler.toModel(deckRepository.save(deck)));
        }

        return ResponseEntity //
                .status(HttpStatus.METHOD_NOT_ALLOWED) //
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                .body(Problem.create() //
                        .withTitle("Method not allowed") //
                        .withDetail("You can't ready a deck that is in the " + deck.getStatus() + " status"));
    }

    @DeleteMapping("/decks/{id}/delete")
    ResponseEntity<?> delete(@PathVariable Long id) {

        Deck deck = deckRepository.findById(id) //
                .orElseThrow(() -> new DeckNotFoundException(id));

        if (deck.getStatus() == Status.WIP) {
            deck.setStatus(Status.DELETED);
            return ResponseEntity.ok(deckModelAssembler.toModel(deckRepository.save(deck)));
        }

        return ResponseEntity //
                .status(HttpStatus.METHOD_NOT_ALLOWED) //
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                .body(Problem.create() //
                        .withTitle("Method not allowed")
                        .withDetail("You can't delete a deck that is in the " + deck.getStatus() + " status"));
    }
}
