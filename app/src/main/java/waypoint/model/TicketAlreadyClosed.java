package waypoint.model;

/** Thrown when a closed ticket is closed again (invariant I5). */
public class TicketAlreadyClosed extends RuntimeException {
    public TicketAlreadyClosed(String message) {
        super(message);
    }
}
