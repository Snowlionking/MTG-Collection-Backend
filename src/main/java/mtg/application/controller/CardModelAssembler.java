package mtg.application.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import mtg.application.entity.Card;

@Component
class CardModelAssembler implements RepresentationModelAssembler<Card, EntityModel<Card>> {

    @Override
    public EntityModel<Card> toModel(Card card) {

        return EntityModel.of(card, //
                linkTo(methodOn(CardController.class).findById(card.getId())).withSelfRel(),
                linkTo(methodOn(CardController.class).findAll()).withRel("cards"));
    }
}
