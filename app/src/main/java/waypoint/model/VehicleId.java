package waypoint.model;

import java.util.Objects;

/** Distinct id type so it can never be swapped with another id at a call site. */
public record VehicleId(String value) {
    public VehicleId {
        Objects.requireNonNull(value, "value");
        if (value.isBlank()) throw new IllegalArgumentException("VehicleId must not be blank");
    }
}
