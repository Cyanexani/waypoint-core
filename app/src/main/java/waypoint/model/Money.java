package waypoint.model;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

/** VALUE OBJECT. Exact money held as minor units (paise). No double or float. */
public final class Money implements Comparable<Money> {
    private static final Currency INR = Currency.getInstance("INR");

    private final long minor;
    private final Currency currency;

    private Money(long minor, Currency currency) {
        this.minor = minor;
        this.currency = Objects.requireNonNull(currency, "currency");
    }

    public static Money ofMinor(long minor, Currency currency) {
        return new Money(minor, currency);
    }

    public static Money rupees(long rupees) {
        return new Money(Math.multiplyExact(rupees, 100L), INR);
    }

    public Money plus(Money other) {
        requireSameCurrency(other);
        return new Money(Math.addExact(minor, other.minor), currency);
    }

    public Money minus(Money other) {
        requireSameCurrency(other);
        return new Money(Math.subtractExact(minor, other.minor), currency);
    }

    public Money times(int factor) {
        return new Money(Math.multiplyExact(minor, (long) factor), currency);
    }

    public boolean isNegative() {
        return minor < 0;
    }

    /** Splits into n shares that sum exactly to this amount; earlier shares get the leftover paise. */
    public List<Money> allocate(int n) {
        if (n <= 0) throw new IllegalArgumentException("n must be positive");
        long base = Math.floorDiv(minor, (long) n);
        long remainder = Math.floorMod(minor, (long) n);
        List<Money> shares = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            shares.add(new Money(base + (i < remainder ? 1 : 0), currency));
        }
        return List.copyOf(shares);
    }

    public long minor() {
        return minor;
    }

    public Currency currency() {
        return currency;
    }

    private void requireSameCurrency(Money other) {
        Objects.requireNonNull(other, "other");
        if (!currency.equals(other.currency)) {
            throw new CurrencyMismatch(currency + " vs " + other.currency);
        }
    }

    @Override
    public int compareTo(Money other) {
        requireSameCurrency(other);
        return Long.compare(minor, other.minor);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Money m && m.minor == minor && m.currency.equals(currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minor, currency);
    }

    @Override
    public String toString() {
        long abs = Math.abs(minor);
        return (minor < 0 ? "-" : "") + currency.getCurrencyCode() + " " + abs / 100 + "."
                + String.format("%02d", abs % 100);
    }
}
