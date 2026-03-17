package org.example.parser;

import java.util.Objects;

/**
 * An immutable token produced by {@link ScriptParser}.
 *
 * <p>A token pairs a {@link TokenType} (whether it is an opcode or a raw
 * data value) with the original string from the script source.
 *
 * @param type  whether this token is an {@code OPERATOR} or {@code DATA}
 * @param value the raw string as it appeared in the script
 */
public record Token(TokenType type, String value) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token data = (Token) o;
        return type == data.type &&
                Objects.equals(value, data.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }
}
