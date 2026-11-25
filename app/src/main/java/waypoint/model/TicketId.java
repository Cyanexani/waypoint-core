package waypoint.model;

import java.util.Objects;

/** Distinct id type so it can never be swapped with another id at a call site. */
public record TicketId(String value) {
    public TicketId {
        Objects.requireNonNull(value, "value");
        if (value.isBlank()) throw new IllegalArgumentException("TicketId must not be blank");
    }
}
