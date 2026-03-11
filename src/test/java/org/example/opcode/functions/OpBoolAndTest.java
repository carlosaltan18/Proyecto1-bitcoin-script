package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OpBoolAndTest {
    private ExecutionContext context;
    private OpBoolAnd opBoolAnd;

    @BeforeEach
    public void setUp() {
        context = new ExecutionContext();
        opBoolAnd = new OpBoolAnd();
    }

    @Test
    public void shouldReturnOneIfBothAreTrue() {
        context.getStack().push(new byte[]{1}); // a
        context.getStack().push(new byte[]{1}); // b

        opBoolAnd.execute(context);

        assertEquals(1, context.getStack().size());
        assertArrayEquals(new byte[]{1}, context.getStack().pop());
    }

    @Test
    public void shouldReturnZeroIfOneIsFalse() {
        context.getStack().push(new byte[]{1}); // a
        context.getStack().push(new byte[]{0}); // b

        opBoolAnd.execute(context);

        assertEquals(1, context.getStack().size());
        assertArrayEquals(new byte[]{0}, context.getStack().pop());
    }

    @Test
    public void shouldTreatNonZeroAsTrue() {
        context.getStack().push(new byte[]{5});  // true
        context.getStack().push(new byte[]{10}); // true

        opBoolAnd.execute(context);

        assertEquals(1, context.getStack().size());
        assertArrayEquals(new byte[]{1}, context.getStack().pop());
    }

    @Test
    public void shouldFailIfStackHasLessEqualThanTwoElements() {
        context.getStack().push(new byte[]{1});

        RuntimeException exception = assertThrows(RuntimeException.class, () -> opBoolAnd.execute(context));
        assertEquals("OP_BOOLAND requires two elements", exception.getMessage());
    }

    @Test
    public void shouldPopOperandsAndPushNewResult() {
        context.getStack().push(new byte[]{99}); // Elemento previo en la pila
        context.getStack().push(new byte[]{1});
        context.getStack().push(new byte[]{0});

        opBoolAnd.execute(context);

        assertEquals(2, context.getStack().size());
        assertArrayEquals(new byte[]{0}, context.getStack().pop()); // Resultado del AND
        assertArrayEquals(new byte[]{99}, context.getStack().pop()); // Elemento original
    }
}