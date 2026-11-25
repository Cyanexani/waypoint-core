package waypoint.model;

import java.util.Objects;

/** ENTITY. Identity = id. Invariant: a spot cannot be assigned while occupied. */
public final class Spot {
    private final SpotId id;
    private VehicleId occupant;   // the ONLY mutable field

    public Spot(SpotId id) {
        this.id = Objects.requireNonNull(id, "id");
    }

    public SpotId id() {
        return id;
    }

    /** True only if this call took the spot. */
    public boolean tryAssign(VehicleId v) {
        Objects.requireNonNull(v, "v");
        if (occupant != null) return false;
        occupant = v;
        return true;
    }

    /** Idempotent; releasing a free spot, or one held by another vehicle, is not an error. */
    public void release(VehicleId v) {
        if (v != null && v.equals(occupant)) occupant = null;
    }

    public boolean isFree() {
        return occupant == null;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Spot s && s.id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Spot[" + id.value() + "]";
    }
}
