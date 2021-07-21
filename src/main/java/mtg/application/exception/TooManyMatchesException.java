package mtg.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class TooManyMatchesException extends Exception {

    private static final long serialVersionUID = 1L;

    public TooManyMatchesException(String message) {
        super(message);
    }

}
