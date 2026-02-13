package org.example.interpreter;

import org.example.parser.Token;
import org.example.parser.TokenType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScriptInterpreterTest {

    @Test
    void testValidDataReturnsTrue() {
        ScriptInterpreter interpreter = new ScriptInterpreter();

        List<Token> tokens = List.of(
                new Token(TokenType.DATA, "A")
        );

        boolean result = interpreter.execute(tokens);

        assertTrue(result);
    }

    @Test
    void testEmptyScriptReturnsFalse() {
        ScriptInterpreter interpreter = new ScriptInterpreter();

        boolean result = interpreter.execute(List.of());

        assertFalse(result);
    }

    @Test
    void testUnknownOpcodeReturnsFalse() {
        ScriptInterpreter interpreter = new ScriptInterpreter();

        List<Token> tokens = List.of(
                new Token(TokenType.OPERATOR, "OP_UNKNOWN")
        );

        boolean result = interpreter.execute(tokens);

        assertFalse(result);
    }

    @Test
    void testPushZeroReturnsFalse() {
        ScriptInterpreter interpreter = new ScriptInterpreter();

        List<Token> tokens = List.of(
                new Token(TokenType.OPERATOR, "OP_0")
        );

        boolean result = interpreter.execute(tokens);

        assertFalse(result);
    }

    @Test
    void testTwoEqualValuesWithOpEqualReturnsTrue() {
        ScriptInterpreter interpreter = new ScriptInterpreter();

        List<Token> tokens = List.of(
                new Token(TokenType.DATA, "A"),
                new Token(TokenType.DATA, "A"),
                new Token(TokenType.OPERATOR, "OP_EQUAL")
        );

        boolean result = interpreter.execute(tokens);

        assertTrue(result);
    }

    @Test
    void testTwoDifferentValuesWithOpEqualReturnsFalse() {
        ScriptInterpreter interpreter = new ScriptInterpreter();

        List<Token> tokens = List.of(
                new Token(TokenType.DATA, "A"),
                new Token(TokenType.DATA, "B"),
                new Token(TokenType.OPERATOR, "OP_EQUAL")
        );

        boolean result = interpreter.execute(tokens);

        assertFalse(result);
    }
}
