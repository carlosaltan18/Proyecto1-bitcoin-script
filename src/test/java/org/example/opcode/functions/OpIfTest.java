package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OpIfTest {
    private ExecutionContext context;
    private OpIf opIf;

    @BeforeEach
    public void setUp() {
        context = new ExecutionContext();
        opIf = new OpIf();
    }

    @Test
    public void shouldPushTrueToExecStackWhenTopIsNonZero() {
        context.getStack().push(new byte[]{1});
        opIf.execute(context);

        assertEquals(0, context.getStack().size());
        assertTrue(context.getExecStack().peek());
    }

    @Test
    public void shouldPushFalseToExecStackWhenTopIsZero() {
        context.getStack().push(new byte[]{0});
        opIf.execute(context);

        assertFalse(context.getExecStack().peek());
    }

    @Test
    public void shouldPushFalseWhenTopIsEmptyArray() {
        context.getStack().push(new byte[]{});
        opIf.execute(context);

        assertFalse(context.getExecStack().peek());
    }

    @Test
    public void shouldTreatAnyNonZeroByteAsTrue() {
        context.getStack().push(new byte[]{5});
        opIf.execute(context);

        assertTrue(context.getExecStack().peek());
    }

    @Test
    public void shouldThrowWhenMainStackIsEmpty() {
        RuntimeException ex = assertThrows(RuntimeException.class, () -> opIf.execute(context));
        assertEquals("OP_IF requires one element on the stack", ex.getMessage());
    }

    @Test
    public void shouldPushFalseFrameWithoutPoppingWhenAlreadyInSkippedBranch() {
        // Simulate being inside a false branch already.
        context.getExecStack().push(false);
        context.getStack().push(new byte[]{1});

        opIf.execute(context);

        // Main stack must be untouched — no pop happened.
        assertEquals(1, context.getStack().size());
        // Nested frame must also be false.
        assertFalse(context.getExecStack().peek());
        // Both frames still present.
        context.getExecStack().pop();
        assertFalse(context.getExecStack().peek());
    }

    @Test
    public void shouldSupportTrueNestedInsideTrueBranch() {
        context.getStack().push(new byte[]{1});
        opIf.execute(context);

        context.getStack().push(new byte[]{1});
        opIf.execute(context);

        assertTrue(context.getExecStack().peek()); // inner
        context.getExecStack().pop();
        assertTrue(context.getExecStack().peek()); // outer
    }
}
