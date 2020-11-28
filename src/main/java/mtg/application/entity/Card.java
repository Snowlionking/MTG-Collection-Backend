package mtg.application.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Card {

    @Id
    @GeneratedValue
    private Long id;

    private String cardName;

    private String type;

    public Card() {
        super();
    }

    public Card(String cardName, String type) {
        super();
        this.cardName = cardName;
        this.type = type;
    }
}
