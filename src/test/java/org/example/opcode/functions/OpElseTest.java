package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.helpers.ExecState;
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
    public void shouldFlipExecutingToNotExecuting() {
        context.getExecStack().push(ExecState.EXECUTING);

        opElse.execute(context);

        assertEquals(ExecState.NOT_EXECUTING, context.getExecStack().peek());
        assertFalse(context.isExecuting());
    }

    @Test
    public void shouldFlipNotExecutingToExecuting() {
        context.getExecStack().push(ExecState.NOT_EXECUTING);

        opElse.execute(context);

        assertEquals(ExecState.EXECUTING, context.getExecStack().peek());
        assertTrue(context.isExecuting());
    }


    @Test
    public void shouldMaintainParentNotExecutingState() {
        // Caso crítico: Si el padre es falso, el ELSE interno NO debe activar la ejecución
        context.getExecStack().push(ExecState.PARENT_NOT_EXECUTING);

        opElse.execute(context);

        // Debe seguir siendo PARENT_NOT_EXECUTING (no se voltea a EXECUTING)
        assertEquals(ExecState.PARENT_NOT_EXECUTING, context.getExecStack().peek());
        assertFalse(context.isExecuting());
    }

    @Test
    public void shouldOnlyFlipTopFrameInNestedCondition() {
        context.getExecStack().push(ExecState.EXECUTING);     // outer IF
        context.getExecStack().push(ExecState.NOT_EXECUTING); // inner IF (pertenece a rama activa)

        opElse.execute(context);

        assertEquals(ExecState.EXECUTING, context.getExecStack().peek()); // inner flipped

        context.getExecStack().pop();
        assertEquals(ExecState.EXECUTING, context.getExecStack().peek()); // outer unchanged
    }

    @Test
    public void shouldWorkWithMultipleElseOperations() {
        context.getExecStack().push(ExecState.EXECUTING);

        opElse.execute(context);
        assertEquals(ExecState.NOT_EXECUTING, context.getExecStack().peek());

        opElse.execute(context);
        assertEquals(ExecState.EXECUTING, context.getExecStack().peek());
    }
}