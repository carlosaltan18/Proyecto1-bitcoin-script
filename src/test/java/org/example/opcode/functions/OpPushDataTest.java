package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpPushDataTest {
    @Test
    void testPushDataAddsElement() {
        ExecutionContext context = new ExecutionContext();
        OpPushData op = new OpPushData("hello".getBytes());

        op.execute(context);

        assertEquals(1, context.getStack().size());
    }

    @Test
    void testPushDataCorrectValue() {
        ExecutionContext context = new ExecutionContext();
        byte[] expected = "test".getBytes();

        OpPushData op = new OpPushData(expected);
        op.execute(context);

        assertArrayEquals(expected, (byte[]) context.getStack().peek());
    }

    @Test
    void testMultiplePushData() {
        ExecutionContext context = new ExecutionContext();

        new OpPushData("A".getBytes()).execute(context);
        new OpPushData("B".getBytes()).execute(context);

        assertEquals(2, context.getStack().size());
    }

    @Test
    void testPushEmptyData() {
        ExecutionContext context = new ExecutionContext();

        new OpPushData(new byte[0]).execute(context);

        assertEquals(0, context.getStack().peek().length);
    }

    @Test
    void testPushDataDoesNotThrowException() {
        ExecutionContext context = new ExecutionContext();

        assertDoesNotThrow(() ->
                new OpPushData("safe".getBytes()).execute(context));
    }

}