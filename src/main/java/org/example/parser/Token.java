package org.example.parser;

import java.util.Objects;

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
