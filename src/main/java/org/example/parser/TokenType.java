package org.example.parser;

/**
 * Classifies a {@link Token} produced by the script parser.
 */
public enum TokenType {
    /** A known opcode such as {@code OP_DUP} or {@code OP_CHECKSIG}. */
    OPERATOR,
    /** A raw data value to be pushed onto the stack as a byte array. */
    DATA,
}
