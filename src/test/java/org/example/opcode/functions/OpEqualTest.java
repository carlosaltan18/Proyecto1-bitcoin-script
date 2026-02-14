package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OpEqualTest {
    private OpEqual opEqual;
    private ExecutionContext context;

    @BeforeEach
    public void setUp() {
        opEqual = new OpEqual();
        context = new ExecutionContext();
    }

    @Test
    void shouldReturnTrueIfOpEqual() {
        var stack = context.getStack();
        stack.push(new byte[]{10});
        stack.push(new byte[]{10});

        opEqual.execute(context);

        assertEquals(1, stack.size());
        assertArrayEquals(new byte[]{1}, stack.pop());
    }

    @Test
    void shouldPushFalseWhenElementsAreDifferent() {
        var stack = context.getStack();

        stack.push(new byte[]{10});
        stack.push(new byte[]{20});

        opEqual.execute(context);

        assertEquals(1, stack.size());
        assertArrayEquals(new byte[]{0}, stack.pop());
    }

    @Test
    void shouldFailIfStackHasLessThanTwoElements() {
        var stack = context.getStack();

        stack.push(new byte[]{10});

        assertThrows(RuntimeException.class, () -> opEqual.execute(context));
    }

    @Test
    void shouldWorkWithMultiByteArrays() {
        var stack = context.getStack();

        stack.push(new byte[]{1, 2, 3});
        stack.push(new byte[]{1, 2, 3});

        opEqual.execute(context);

        assertArrayEquals(new byte[]{1}, stack.pop());
    }

    @Test
    void shouldReturnFalseForDifferentLengthArrays() {
        var stack = context.getStack();

        stack.push(new byte[]{1, 2});
        stack.push(new byte[]{1, 2, 3});

        opEqual.execute(context);

        assertArrayEquals(new byte[]{0}, stack.pop());
    }
}
