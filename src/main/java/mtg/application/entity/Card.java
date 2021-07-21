package mtg.application.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@Entity
@AllArgsConstructor
@Table(name = "Cards")
public class Card {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cardTypes_id")
    private CardTypes cardTypes;

    private String cardName;
    private String cardEffect;
    private String flavourText;
    private String power;
    private String toughness;
    private String manaCost;
    private String imageUrl;

    private double convertedManaCost;

    private int numberOwn;

}
