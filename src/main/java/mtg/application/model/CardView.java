package mtg.application.model;

import java.io.Serializable;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CardView implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String imageUrl;
    private int multiverseId;

}
