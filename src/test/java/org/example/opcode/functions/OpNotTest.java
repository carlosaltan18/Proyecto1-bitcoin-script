package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OpNotTest {
    private ExecutionContext context;
    private OpNot opNot;

    @BeforeEach
    public void setUp() {
        context = new ExecutionContext();
        opNot = new OpNot();
    }

    @Test
    public void shouldNegateFalseToTrue() {
        context.getStack().push(new byte[]{0});

        opNot.execute(context);

        assertEquals(1, context.getStack().size());
        assertArrayEquals(new byte[]{1}, context.getStack().pop());
    }

    @Test
    public void shouldNegateTrueToFalse() {
        context.getStack().push(new byte[]{1});

        opNot.execute(context);

        assertEquals(1, context.getStack().size());
        assertArrayEquals(new byte[]{0}, context.getStack().pop());
    }

    @Test
    public void shouldTreatNonZeroAsTrueAndNegateToFalse() {
        context.getStack().push(new byte[]{5});

        opNot.execute(context);

        assertEquals(1, context.getStack().size());
        assertArrayEquals(new byte[]{0}, context.getStack().pop());
    }

    @Test
    public void shouldFailIfStackIsEmpty() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> opNot.execute(context));
        assertEquals("OP_NOT requires one element", exception.getMessage());
    }

    @Test
    public void shouldNotAffectOtherElementsInStack() {
        context.getStack().push(new byte[]{100});
        context.getStack().push(new byte[]{1});

        opNot.execute(context);

        assertEquals(2, context.getStack().size());
        assertArrayEquals(new byte[]{0}, context.getStack().pop());
        assertArrayEquals(new byte[]{100}, context.getStack().pop());
    }
}