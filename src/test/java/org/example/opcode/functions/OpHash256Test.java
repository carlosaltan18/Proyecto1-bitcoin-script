package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpHash256Test {

    private ExecutionContext context;
    private OpHash256 opHash256;

    @BeforeEach
    void setUp() {
        context = new ExecutionContext();
        opHash256 = new OpHash256();
    }

    @Test
    void shouldReplaceTopWithHash() {
        context.getStack().push(new byte[]{1, 2, 3, 4, 5});
        opHash256.execute(context);
        assertEquals(1, context.getStack().size());
    }

    @Test
    void shouldReturnAtMostFourBytes() {
        context.getStack().push(new byte[]{10, 20, 30, 40, 50});
        opHash256.execute(context);
        assertTrue(context.getStack().peek().length <= 4);
    }

    @Test
    void shouldBeDeterministic() {
        byte[] input = new byte[]{1, 2, 3};
        context.getStack().push(input.clone());
        opHash256.execute(context);
        byte[] first = context.getStack().pop();

        context.getStack().push(input.clone());
        opHash256.execute(context);
        byte[] second = context.getStack().pop();

        assertArrayEquals(first, second);
    }

    @Test
    void shouldThrowWhenStackIsEmpty() {
        assertThrows(RuntimeException.class, () -> opHash256.execute(context));
    }
}
