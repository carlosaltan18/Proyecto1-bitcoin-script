package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.helpers.ExecState;
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
        // Cambiado de true a ExecState.EXECUTING
        context.getExecStack().push(ExecState.EXECUTING);

        opEndIf.execute(context);

        assertTrue(context.getExecStack().isEmpty());
    }

    @Test
    public void shouldRemoveOnlyTopFrameInNestedConditions() {
        // Simulando: IF (true) { IF (false) { ... } }
        context.getExecStack().push(ExecState.EXECUTING);       // outer IF
        context.getExecStack().push(ExecState.NOT_EXECUTING);  // inner IF

        opEndIf.execute(context);

        // Verificamos que quede el estado del IF exterior
        assertEquals(ExecState.EXECUTING, context.getExecStack().peek());
    }


    @Test
    public void shouldSupportMultipleEndIfOperations() {
        context.getExecStack().push(ExecState.EXECUTING);
        context.getExecStack().push(ExecState.PARENT_NOT_EXECUTING);

        opEndIf.execute(context);
        assertEquals(ExecState.EXECUTING, context.getExecStack().peek());

        opEndIf.execute(context);
        assertTrue(context.getExecStack().isEmpty());
    }

    @Test
    public void shouldWorkAfterElseFlip() {
        context.getExecStack().push(ExecState.EXECUTING);

        OpElse opElse = new OpElse();
        opElse.execute(context); // Cambia EXECUTING -> NOT_EXECUTING

        // Verificamos estado intermedio
        assertEquals(ExecState.NOT_EXECUTING, context.getExecStack().peek());

        opEndIf.execute(context);

        assertTrue(context.getExecStack().isEmpty());
    }
}