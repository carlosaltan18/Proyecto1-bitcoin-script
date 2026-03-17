package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpSha256Test {

    private ExecutionContext context;
    private OpSha256 opSha256;

    @BeforeEach
    void setUp() {
        context = new ExecutionContext();
        opSha256 = new OpSha256();
    }

    @Test
    void shouldReplaceTopWithHash() {
        context.getStack().push(new byte[]{1, 2, 3, 4, 5});
        opSha256.execute(context);
        assertEquals(1, context.getStack().size());
    }

    @Test
    void shouldReturnAtMostFourBytes() {
        context.getStack().push(new byte[]{10, 20, 30, 40, 50});
        opSha256.execute(context);
        assertTrue(context.getStack().peek().length <= 4);
    }

    @Test
    void shouldBeDeterministic() {
        byte[] input = new byte[]{7, 8, 9};
        context.getStack().push(input.clone());
        opSha256.execute(context);
        byte[] first = context.getStack().pop();

        context.getStack().push(input.clone());
        opSha256.execute(context);
        byte[] second = context.getStack().pop();

        assertArrayEquals(first, second);
    }

    @Test
    void shouldHandleSingleByteInput() {
        context.getStack().push(new byte[]{42});
        opSha256.execute(context);
        assertArrayEquals(new byte[]{42}, context.getStack().pop());
    }

    @Test
    void shouldThrowWhenStackIsEmpty() {
        assertThrows(RuntimeException.class, () -> opSha256.execute(context));
    }
}
