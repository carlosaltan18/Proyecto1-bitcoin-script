package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpSwapTest {

    private ExecutionContext context;
    private OpSwap opSwap;

    @BeforeEach
    void setUp() {
        context = new ExecutionContext();
        opSwap = new OpSwap();
    }

    @Test
    void shouldSwapTopTwoElements() {
        context.getStack().push(new byte[]{1});
        context.getStack().push(new byte[]{2});

        opSwap.execute(context);

        assertArrayEquals(new byte[]{1}, context.getStack().pop());
        assertArrayEquals(new byte[]{2}, context.getStack().pop());
    }

    @Test
    void shouldLeaveStackSizeUnchanged() {
        context.getStack().push(new byte[]{1});
        context.getStack().push(new byte[]{2});

        opSwap.execute(context);

        assertEquals(2, context.getStack().size());
    }

    @Test
    void shouldThrowWhenStackHasOnlyOneElement() {
        context.getStack().push(new byte[]{1});
        assertThrows(RuntimeException.class, () -> opSwap.execute(context));
    }

    @Test
    void shouldThrowWhenStackIsEmpty() {
        assertThrows(RuntimeException.class, () -> opSwap.execute(context));
    }
}
