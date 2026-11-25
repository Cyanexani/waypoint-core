package waypoint.model;

/** Thrown when Money in different currencies is combined. */
public class CurrencyMismatch extends RuntimeException {
    public CurrencyMismatch(String message) {
        super(message);
    }
}
