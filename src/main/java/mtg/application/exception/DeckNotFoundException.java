package mtg.application.exception;

public class DeckNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DeckNotFoundException(Long id) {
        super("Could not find deck " + id);
    }

}
