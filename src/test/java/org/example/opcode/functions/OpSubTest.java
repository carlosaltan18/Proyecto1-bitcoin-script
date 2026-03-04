package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OpSubTest {
    private ExecutionContext context;
    private OpSub opSub;

    @BeforeEach
    public void setUp() {
        context = new ExecutionContext();
        opSub = new OpSub();
    }

    @Test
    public void shouldSubtractTwoElementsInCorrectOrder() {
        context.getStack().push(new byte[]{10});
        context.getStack().push(new byte[]{3});

        opSub.execute(context);

        assertEquals(1, context.getStack().size());
        assertArrayEquals(new byte[]{7}, context.getStack().pop());
    }

    @Test
    public void shouldHandleNegativeResults() {
        context.getStack().push(new byte[]{5});
        context.getStack().push(new byte[]{12});

        opSub.execute(context);

        assertArrayEquals(new byte[]{-7}, context.getStack().pop());
    }

    @Test
    public void shouldFailIfStackHasLessThanTwoElements() {
        assertThrows(RuntimeException.class, () -> opSub.execute(context), "OP_SUB requires two elements");

        context.getStack().push(new byte[]{10});
        assertThrows(RuntimeException.class, () -> opSub.execute(context), "OP_SUB requires two elements");
    }

    @Test
    public void resultShouldBeANewIndependentByteArray() {
        context.getStack().push(new byte[]{20});
        context.getStack().push(new byte[]{5});

        opSub.execute(context);

        byte[] result = context.getStack().pop();

        assertEquals(15, result[0]);
        result[0] = 99;
        assertNotEquals(15, result[0]);
    }
}