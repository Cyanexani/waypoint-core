package waypoint.model;

import java.time.Instant;
import java.util.Objects;

/** ENTITY. Invariants:
 *   I1  entry is always present
 *   I2  exit is null, or not before entry
 *   I3  fare is null, or not negative
 *   I4  fare exists only if exit exists
 *   I5  a closed ticket never reopens
 */
public final class Ticket {
    private final TicketId id;
    private final SpotId spot;
    private final Instant entry;
    private Instant exit;   // non-final
    private Money fare;     // non-final

    private Ticket(TicketId id, SpotId spot, Instant entry) {
        this.id = Objects.requireNonNull(id, "id");
        this.spot = Objects.requireNonNull(spot, "spot");
        this.entry = Objects.requireNonNull(entry, "entry");
    }

    public static Ticket open(TicketId id, SpotId spot, Instant entry) {
        return new Ticket(id, spot, entry);
    }

    public boolean isOpen() {
        return exit == null;
    }

    public Money close(Instant at, FarePolicy policy) {
        Objects.requireNonNull(at, "at");
        Objects.requireNonNull(policy, "policy");
        if (!isOpen()) throw new TicketAlreadyClosed("Ticket " + id.value() + " is already closed");
        if (at.isBefore(entry)) throw new IllegalArgumentException("exit before entry");
        Money computed = policy.fareFor(entry, at, spot);
        if (computed == null || computed.isNegative()) {
            throw new IllegalArgumentException("fare must be non-negative");
        }
        // exit and fare are set together, only after everything has been validated
        this.exit = at;
        this.fare = computed;
        return computed;
    }

    public TicketId id() { return id; }
    public SpotId spot() { return spot; }
    public Instant entry() { return entry; }
    public Instant exit() { return exit; }
    public Money fare() { return fare; }
}
