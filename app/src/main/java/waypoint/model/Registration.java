package waypoint.model;

import java.util.Objects;
import java.util.regex.Pattern;

/** VALUE OBJECT. Validated and normalised so an invalid instance cannot exist. */
public record Registration(String value) {
    // Two letters, two digits, one or two letters, four digits. e.g. MH12AB1234
    private static final Pattern FORMAT = Pattern.compile("[A-Z]{2}[0-9]{2}[A-Z]{1,2}[0-9]{4}");

    public Registration {
        Objects.requireNonNull(value, "value");
        value = value.trim().toUpperCase();
        if (!FORMAT.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid registration: " + value);
        }
    }
}
