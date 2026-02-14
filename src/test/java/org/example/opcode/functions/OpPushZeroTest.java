package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpPushZeroTest {
    @Test
    void testPushZeroAddsElement() {
        ExecutionContext context = new ExecutionContext();
        OpPushZero op = new OpPushZero();

        op.execute(context);

        assertEquals(1, context.getStack().size());
    }

    @Test
    void testPushZeroPushesEmptyArray() {
        ExecutionContext context = new ExecutionContext();
        OpPushZero op = new OpPushZero();

        op.execute(context);

        var result = context.getStack().peek();
        assertEquals(0, result.length);
    }

    @Test
    void testStackNotEmptyAfterPushZero() {
        ExecutionContext context = new ExecutionContext();
        new OpPushZero().execute(context);

        assertFalse(context.getStack().isEmpty());
    }

    @Test
    void testMultiplePushZero() {
        ExecutionContext context = new ExecutionContext();

        new OpPushZero().execute(context);
        new OpPushZero().execute(context);

        assertEquals(2, context.getStack().size());
    }

    @Test
    void testPushZeroDoesNotThrowException() {
        ExecutionContext context = new ExecutionContext();
        assertDoesNotThrow(() -> new OpPushZero().execute(context));
    }

}