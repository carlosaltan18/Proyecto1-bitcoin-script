package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.helpers.ExecState;
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
    public void shouldPushExecutingToExecStackWhenTopIsNonZero() {
        context.getStack().push(new byte[]{1});
        opIf.execute(context);

        assertEquals(0, context.getStack().size());
        assertEquals(ExecState.EXECUTING, context.getExecStack().peek());
        assertTrue(context.isExecuting());
    }

    @Test
    public void shouldPushNotExecutingToExecStackWhenTopIsZero() {
        context.getStack().push(new byte[]{0});
        opIf.execute(context);

        assertEquals(ExecState.NOT_EXECUTING, context.getExecStack().peek());
        assertFalse(context.isExecuting());
    }

    @Test
    public void shouldPushNotExecutingWhenTopIsEmptyArray() {
        context.getStack().push(new byte[]{});
        opIf.execute(context);

        assertEquals(ExecState.NOT_EXECUTING, context.getExecStack().peek());
    }

    @Test
    public void shouldTreatAnyNonZeroByteAsTrue() {
        context.getStack().push(new byte[]{0x05});
        opIf.execute(context);

        assertEquals(ExecState.EXECUTING, context.getExecStack().peek());
    }



    @Test
    public void shouldPushParentNotExecutingWithoutPoppingWhenAlreadyInSkippedBranch() {
        // Simulamos estar dentro de una rama falsa ya existente
        context.getExecStack().push(ExecState.NOT_EXECUTING);
        context.getStack().push(new byte[]{1}); // Este valor no debería consumirse

        opIf.execute(context);

        // El stack principal debe estar intacto (no hubo pop)
        assertEquals(1, context.getStack().size());

        // El nuevo frame debe ser PARENT_NOT_EXECUTING
        assertEquals(ExecState.PARENT_NOT_EXECUTING, context.getExecStack().peek());
        assertFalse(context.isExecuting());
    }

    @Test
    public void shouldSupportTrueNestedInsideTrueBranch() {
        // Primer IF (true)
        context.getStack().push(new byte[]{1});
        opIf.execute(context);

        // Segundo IF (true)
        context.getStack().push(new byte[]{1});
        opIf.execute(context);

        assertEquals(ExecState.EXECUTING, context.getExecStack().peek()); // tope
        assertTrue(context.isExecuting());

        context.getExecStack().pop();
        assertEquals(ExecState.EXECUTING, context.getExecStack().peek()); // padre
    }
}