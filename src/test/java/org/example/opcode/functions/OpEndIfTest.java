package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OpEndIfTest {
    private ExecutionContext context;
    private OpEndIf opEndIf;

    @BeforeEach
    public void setUp() {
        context = new ExecutionContext();
        opEndIf = new OpEndIf();
    }

    @Test
    public void shouldRemoveExecutionFrame() {
        context.getExecStack().push(true);

        opEndIf.execute(context);

        assertTrue(context.getExecStack().isEmpty());
    }

    @Test
    public void shouldRemoveOnlyTopFrameInNestedConditions() {
        context.getExecStack().push(true);   // outer IF
        context.getExecStack().push(false);  // inner IF

        opEndIf.execute(context);

        assertTrue(context.getExecStack().peek()); // outer remains
    }

    @Test
    public void shouldThrowWhenNoMatchingIfExists() {
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> opEndIf.execute(context));

        assertEquals("No matching IF condition", ex.getMessage());
    }

    @Test
    public void shouldSupportMultipleEndIfOperations() {
        context.getExecStack().push(true);
        context.getExecStack().push(false);

        opEndIf.execute(context);
        assertTrue(context.getExecStack().peek());

        opEndIf.execute(context);
        assertTrue(context.getExecStack().isEmpty());
    }

    @Test
    public void shouldWorkAfterElseFlip() {
        context.getExecStack().push(true);

        OpElse opElse = new OpElse();
        opElse.execute(context); // flips to false

        opEndIf.execute(context);

        assertTrue(context.getExecStack().isEmpty());
    }
}
