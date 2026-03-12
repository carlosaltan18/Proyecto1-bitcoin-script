package org.example.interpreter;

import org.example.parser.Token;
import org.example.parser.TokenType;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScriptInterpreterTest {

    // -------------------------------------------------------------------------
    // Existing baseline tests
    // -------------------------------------------------------------------------

    @Test
    void testValidDataReturnsTrue() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        assertTrue(interpreter.execute(List.of(new Token(TokenType.DATA, "A"))));
    }

    @Test
    void testEmptyScriptReturnsFalse() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        assertFalse(interpreter.execute(List.of()));
    }

    @Test
    void testUnknownOpcodeReturnsFalse() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        assertFalse(interpreter.execute(List.of(new Token(TokenType.OPERATOR, "OP_UNKNOWN"))));
    }

    @Test
    void testPushZeroReturnsFalse() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        assertFalse(interpreter.execute(List.of(new Token(TokenType.OPERATOR, "OP_0"))));
    }

    @Test
    void testTwoEqualValuesWithOpEqualReturnsTrue() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        assertTrue(interpreter.execute(List.of(
                new Token(TokenType.DATA, "A"),
                new Token(TokenType.DATA, "A"),
                new Token(TokenType.OPERATOR, "OP_EQUAL")
        )));
    }

    @Test
    void testTwoDifferentValuesWithOpEqualReturnsFalse() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        assertFalse(interpreter.execute(List.of(
                new Token(TokenType.DATA, "A"),
                new Token(TokenType.DATA, "B"),
                new Token(TokenType.OPERATOR, "OP_EQUAL")
        )));
    }

    // -------------------------------------------------------------------------
    // OP_IF exec-stack tests
    // -------------------------------------------------------------------------

    // True condition: the data inside the if-branch must be executed.
    @Test
    void testOpIfWithTrueConditionExecutesBranch() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        // OP_1 pushes 1 → OP_IF pops it (true) → PUSHDATA "hello" executes
        // At the end the stack has "hello" (non-zero first byte) → true
        List<Token> tokens = List.of(
                new Token(TokenType.OPERATOR, "OP_1"),
                new Token(TokenType.OPERATOR, "OP_IF"),
                new Token(TokenType.DATA, "hello")
        );
        assertTrue(interpreter.execute(tokens));
    }

    // False condition: data inside the branch must be skipped entirely.
    @Test
    void testOpIfWithFalseConditionSkipsBranch() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        // OP_0 pushes 0 → OP_IF pops it (false) → "hello" is skipped
        // Stack is empty at the end → false
        List<Token> tokens = List.of(
                new Token(TokenType.OPERATOR, "OP_0"),
                new Token(TokenType.OPERATOR, "OP_IF"),
                new Token(TokenType.DATA, "hello")
        );
        assertFalse(interpreter.execute(tokens));
    }

    // An unclosed OP_IF (no OP_ENDIF) must make the script fail.
    @Test
    void testUnclosedOpIfReturnsFalse() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        List<Token> tokens = List.of(
                new Token(TokenType.OPERATOR, "OP_1"),
                new Token(TokenType.OPERATOR, "OP_IF"),
                new Token(TokenType.DATA, "data")
                // no OP_ENDIF
        );
        assertFalse(interpreter.execute(tokens));
    }

    // Skipped branch must not affect the main stack at all.
    @Test
    void testSkippedBranchDoesNotModifyStack() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        // Push "result", then enter a false branch that would push "ignored".
        // "result" must remain on top at the end.
        List<Token> tokens = List.of(
                new Token(TokenType.DATA, "result"),
                new Token(TokenType.OPERATOR, "OP_0"),
                new Token(TokenType.OPERATOR, "OP_IF"),
                new Token(TokenType.DATA, "ignored")
        );
        // Stack still has "result" (non-zero first byte 'r') → true
        assertTrue(interpreter.execute(tokens));
    }

    // Nested OP_IF inside a false outer branch must also be skipped.
    @Test
    void testNestedOpIfInsideFalseBranchIsSkipped() {
        ScriptInterpreter interpreter = new ScriptInterpreter();
        // Outer branch is false → inner OP_IF must not pop anything from stack.
        List<Token> tokens = List.of(
                new Token(TokenType.DATA, "result"),  // stays on stack
                new Token(TokenType.OPERATOR, "OP_0"),
                new Token(TokenType.OPERATOR, "OP_IF"),
                    new Token(TokenType.OPERATOR, "OP_1"),  // skipped
                    new Token(TokenType.OPERATOR, "OP_IF"), // skipped (nested)
                        new Token(TokenType.DATA, "inner")  // skipped
        );
        assertTrue(interpreter.execute(tokens));
    }

    // -------------------------------------------------------------------------
    // Trace flag tests
    // -------------------------------------------------------------------------

    @Test
    void testTraceEnabledProducesOutput() {
        ScriptInterpreter interpreter = new ScriptInterpreter();

        PrintStream originalOut = System.out;
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captured));

        try {
            interpreter.execute(List.of(
                    new Token(TokenType.DATA, "hello"),
                    new Token(TokenType.OPERATOR, "OP_DUP"),
                    new Token(TokenType.OPERATOR, "OP_EQUAL")
            ), true);
        } finally {
            System.setOut(originalOut);
        }

        String output = captured.toString();
        assertTrue(output.contains("[PUSH]"), "Expected [PUSH] trace lines");
        assertTrue(output.contains("[OP]"),   "Expected [OP] trace lines");
    }

    @Test
    void testTraceDisabledProducesNoOutput() {
        ScriptInterpreter interpreter = new ScriptInterpreter();

        PrintStream originalOut = System.out;
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captured));

        try {
            interpreter.execute(List.of(
                    new Token(TokenType.DATA, "hello"),
                    new Token(TokenType.OPERATOR, "OP_DUP"),
                    new Token(TokenType.OPERATOR, "OP_EQUAL")
            ), false);
        } finally {
            System.setOut(originalOut);
        }

        assertEquals("", captured.toString());
    }

    @Test
    void testTraceShowsSkippedTokens() {
        ScriptInterpreter interpreter = new ScriptInterpreter();

        PrintStream originalOut = System.out;
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captured));

        try {
            interpreter.execute(List.of(
                    new Token(TokenType.OPERATOR, "OP_0"),
                    new Token(TokenType.OPERATOR, "OP_IF"),
                    new Token(TokenType.DATA, "skipped")
            ), true);
        } finally {
            System.setOut(originalOut);
        }

        assertTrue(captured.toString().contains("[SKIP]"), "Expected [SKIP] for tokens in false branch");
    }
}
