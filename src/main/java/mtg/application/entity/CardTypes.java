package mtg.application.entity;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@Entity
@AllArgsConstructor
@Table(name = "CardTypes")
public class CardTypes {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "cardTypes")
    private Card card;

    @ElementCollection
    private List<String> types;

    @ElementCollection
    private List<String> subTypes;

    @ElementCollection
    private List<String> superTypes;

}
