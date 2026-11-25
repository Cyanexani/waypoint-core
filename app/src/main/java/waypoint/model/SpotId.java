package waypoint.model;

import java.util.Objects;

/** Distinct id type so it can never be swapped with another id at a call site. */
public record SpotId(String value) {
    public SpotId {
        Objects.requireNonNull(value, "value");
        if (value.isBlank()) throw new IllegalArgumentException("SpotId must not be blank");
    }
}
