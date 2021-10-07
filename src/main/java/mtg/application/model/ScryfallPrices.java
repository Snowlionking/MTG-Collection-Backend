package mtg.application.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ScryfallPrices implements Serializable {

    private static final long serialVersionUID = 1L;

    private String eur;
    private String eur_foil;

}
