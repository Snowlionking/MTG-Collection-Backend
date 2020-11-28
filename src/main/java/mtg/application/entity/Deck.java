package mtg.application.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Deck {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private Status status;

    public Deck() {
    }

    public Deck(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }
}
