package mtg.application.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import mtg.application.entity.Card;
import mtg.application.exception.CardNotFoundException;
import mtg.application.repository.CardRepository;

@RestController
public class CardController {

    private final CardRepository cardBaseRepository;

    private final CardModelAssembler cardModelAssembler;

    CardController(CardRepository cardBaseRepository, CardModelAssembler cardModelAssembler) {
        this.cardBaseRepository = cardBaseRepository;
        this.cardModelAssembler = cardModelAssembler;
    }

    @GetMapping("/cards")
    CollectionModel<EntityModel<Card>> findAll() {
        List<EntityModel<Card>> cards = cardBaseRepository.findAll().stream().map(cardModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(cards, linkTo(methodOn(CardController.class).findAll()).withSelfRel());
    }

    @PostMapping("/cards")
    ResponseEntity<?> newCard(@RequestBody Card newCard) {
        EntityModel<Card> entityModel = cardModelAssembler.toModel(cardBaseRepository.save(newCard));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @GetMapping("/cards/{id}")
    EntityModel<Card> findById(@PathVariable Long id) {
        Card card = cardBaseRepository.findById(id).orElseThrow(() -> new CardNotFoundException(id));

        return cardModelAssembler.toModel(card);
    }

    @PutMapping("/cards/{id}")
    ResponseEntity<?> updateCard(@RequestBody Card newCard, @PathVariable Long id) {

        Card updatedCard = cardBaseRepository.findById(id).map(card -> {
            card.setCardName(newCard.getCardName());
            card.setType(newCard.getType());
            return cardBaseRepository.save(card);
        }).orElseGet(() -> {
            newCard.setId(id);
            return cardBaseRepository.save(newCard);
        });

        EntityModel<Card> entityModel = cardModelAssembler.toModel(updatedCard);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);

    }

    @DeleteMapping("/cards/{id}")
    ResponseEntity<?> deleteCard(@PathVariable Long id) {
        cardBaseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
