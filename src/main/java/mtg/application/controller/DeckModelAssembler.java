package mtg.application.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import mtg.application.entity.Deck;
import mtg.application.entity.Status;

@Component
class DeckModelAssembler implements RepresentationModelAssembler<Deck, EntityModel<Deck>> {

    @Override
    public EntityModel<Deck> toModel(Deck deck) {

        EntityModel<Deck> deckModel = EntityModel.of(deck,
                linkTo(methodOn(DeckController.class).findById(deck.getId())).withSelfRel(),
                linkTo(methodOn(DeckController.class).findAll()).withRel("decks"));

        if (deck.getStatus() == Status.WIP) {
            deckModel.add(linkTo(methodOn(DeckController.class).ready(deck.getId())).withRel("ready"));
            deckModel.add(linkTo(methodOn(DeckController.class).delete(deck.getId())).withRel("delete"));
        }
        return deckModel;
    }
}
