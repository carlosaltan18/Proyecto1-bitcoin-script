package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OpBoolOrTest {
    private ExecutionContext context;
    private OpBoolOr opBoolOr;

    @BeforeEach
    public void setUp() {
        context = new ExecutionContext();
        opBoolOr = new OpBoolOr();
    }

    @Test
    public void shouldReturnOneIfAtLeastOneIsTrue() {
        context.getStack().push(new byte[]{1});
        context.getStack().push(new byte[]{0});

        opBoolOr.execute(context);

        assertEquals(1, context.getStack().size());
        assertArrayEquals(new byte[]{1}, context.getStack().pop());
    }

    @Test
    public void shouldReturnOneIfBothAreTrue() {
        context.getStack().push(new byte[]{1});
        context.getStack().push(new byte[]{1});

        opBoolOr.execute(context);

        assertEquals(1, context.getStack().size());
        assertArrayEquals(new byte[]{1}, context.getStack().pop());
    }

    @Test
    public void shouldReturnZeroIfBothAreFalse() {
        context.getStack().push(new byte[]{0});
        context.getStack().push(new byte[]{0});

        opBoolOr.execute(context);

        assertEquals(1, context.getStack().size());
        assertArrayEquals(new byte[]{0}, context.getStack().pop());
    }

    @Test
    public void shouldTreatNonZeroAsTrue() {
        context.getStack().push(new byte[]{5});
        context.getStack().push(new byte[]{0});

        opBoolOr.execute(context);

        assertEquals(1, context.getStack().size());
        assertArrayEquals(new byte[]{1}, context.getStack().pop());
    }

    @Test
    public void shouldFailIfStackHasInsufficientElements() {
        context.getStack().push(new byte[]{1});

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                opBoolOr.execute(context)
        );

        assertEquals("OP_BOOLOR requires two elements", exception.getMessage());
    }

    @Test
    public void shouldMaintainStackIntegrity() {
        context.getStack().push(new byte[]{42});
        context.getStack().push(new byte[]{0});
        context.getStack().push(new byte[]{0});

        opBoolOr.execute(context);

        assertEquals(2, context.getStack().size());
        assertArrayEquals(new byte[]{0}, context.getStack().pop());
        assertArrayEquals(new byte[]{42}, context.getStack().pop());
    }
}