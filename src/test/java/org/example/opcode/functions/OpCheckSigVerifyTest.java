package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpCheckSigVerifyTest {

    private ExecutionContext context;
    private OpCheckSigVerify op;

    @BeforeEach
    void setUp() {
        context = new ExecutionContext();
        op = new OpCheckSigVerify();
    }

    @Test
    void shouldPassAndLeaveStackEmptyWhenBothValid() {
        context.getStack().push(new byte[]{1, 2, 3}); // sig
        context.getStack().push(new byte[]{4, 5, 6}); // pubKey
        op.execute(context);
        assertTrue(context.getStack().isEmpty());
    }

    @Test
    void shouldThrowWhenSigIsEmpty() {
        context.getStack().push(new byte[]{}); // sig
        context.getStack().push(new byte[]{1}); // pubKey
        assertThrows(RuntimeException.class, () -> op.execute(context));
    }

    @Test
    void shouldThrowWhenPubKeyIsEmpty() {
        context.getStack().push(new byte[]{1}); // sig
        context.getStack().push(new byte[]{}); // pubKey
        assertThrows(RuntimeException.class, () -> op.execute(context));
    }

    @Test
    void shouldThrowWhenStackHasFewerThanTwoElements() {
        context.getStack().push(new byte[]{1});
        assertThrows(RuntimeException.class, () -> op.execute(context));
    }

    @Test
    void shouldThrowWhenStackIsEmpty() {
        assertThrows(RuntimeException.class, () -> op.execute(context));
    }
}
