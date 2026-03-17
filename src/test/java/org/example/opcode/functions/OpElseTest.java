package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OpElseTest {
    private ExecutionContext context;
    private OpElse opElse;

    @BeforeEach
    public void setUp() {
        context = new ExecutionContext();
        opElse = new OpElse();
    }

    @Test
    public void shouldFlipTrueToFalse() {
        context.getExecStack().push(true);

        opElse.execute(context);

        assertFalse(context.getExecStack().peek());
    }

    @Test
    public void shouldFlipFalseToTrue() {
        context.getExecStack().push(false);

        opElse.execute(context);

        assertTrue(context.getExecStack().peek());
    }

    @Test
    public void shouldThrowWhenNoMatchingIf() {
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> opElse.execute(context));

        assertEquals("OP_ELSE needs an IF condition", ex.getMessage());
    }

    @Test
    public void shouldOnlyFlipTopFrameInNestedCondition() {
        context.getExecStack().push(true);   // outer IF
        context.getExecStack().push(false);  // inner IF

        opElse.execute(context);

        assertTrue(context.getExecStack().peek()); // flipped inner
        context.getExecStack().pop();
        assertTrue(context.getExecStack().peek()); // outer unchanged
    }

    @Test
    public void shouldWorkWithMultipleElseOperations() {
        context.getExecStack().push(true);

        opElse.execute(context);
        assertFalse(context.getExecStack().peek());

        opElse.execute(context);
        assertTrue(context.getExecStack().peek());
    }
}
