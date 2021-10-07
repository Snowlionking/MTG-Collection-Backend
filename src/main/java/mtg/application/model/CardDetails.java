package mtg.application.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class CardDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    private String multiversId;

}
