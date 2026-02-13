package org.example.interpreter;

import org.example.parser.Token;
import org.example.parser.TokenType;
import org.example.stack.StackScript;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScriptInterpreterTest {

    @Test
    void testValidScriptReturnsTrue() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        StackScript stack = new StackScript();

        List<Token> tokens = List.of(
                new Token(TokenType.DATA, "1"),
                new Token(TokenType.DATA, "1")
        );

        boolean result = interpreter.execute(tokens, stack);

        assertTrue(result);
    }

    @Test
    void testEmptyScriptReturnsFalse() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        StackScript stack = new StackScript();

        boolean result = interpreter.execute(List.of(), stack);

        assertFalse(result);
    }

    @Test
    void testUnknownOpcodeReturnsFalse() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        StackScript stack = new StackScript();

        List<Token> tokens = List.of(
                new Token(TokenType.OPERATOR, "OP_UNKNOWN")
        );

        boolean result = interpreter.execute(tokens, stack);

        assertFalse(result);
    }

    @Test
    void testPushZeroAndCheckFalse() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        StackScript stack = new StackScript();

        List<Token> tokens = List.of(
                new Token(TokenType.OPERATOR, "OP_0")
        );

        boolean result = interpreter.execute(tokens, stack);

        assertFalse(result);
    }

    @Test
    void testScriptWithSingleTrueValue() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        StackScript stack = new StackScript();

        List<Token> tokens = List.of(
                new Token(TokenType.DATA, "A")
        );

        boolean result = interpreter.execute(tokens, stack);

        assertTrue(result);
    }

}