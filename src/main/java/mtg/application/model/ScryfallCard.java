package mtg.application.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ScryfallCard implements Serializable {

    private static final long serialVersionUID = 1L;

    private ScryfallPrices prices;

}
