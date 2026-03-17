package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.helpers.ExecState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpNotIfTest {

    private ExecutionContext context;
    private OpNotIf opNotIf;

    @BeforeEach
    void setUp() {
        context = new ExecutionContext();
        opNotIf = new OpNotIf();
    }

    @Test
    void shouldPushExecutingWhenTopIsZero() {
        context.getStack().push(new byte[]{0});
        opNotIf.execute(context);
        assertEquals(ExecState.EXECUTING, context.getExecStack().peek());
        assertTrue(context.isExecuting());
    }

    @Test
    void shouldPushNotExecutingWhenTopIsNonZero() {
        context.getStack().push(new byte[]{1});
        opNotIf.execute(context);
        assertEquals(ExecState.NOT_EXECUTING, context.getExecStack().peek());
        assertFalse(context.isExecuting());
    }

    @Test
    void shouldPushExecutingWhenTopIsEmptyArray() {
        context.getStack().push(new byte[]{});
        opNotIf.execute(context);
        assertEquals(ExecState.EXECUTING, context.getExecStack().peek());
    }

    @Test
    void shouldPushParentNotExecutingWithoutPoppingWhenAlreadySkipping() {
        context.getExecStack().push(ExecState.NOT_EXECUTING);
        context.getStack().push(new byte[]{0});

        opNotIf.execute(context);

        assertEquals(1, context.getStack().size()); // stack untouched
        assertEquals(ExecState.PARENT_NOT_EXECUTING, context.getExecStack().peek());
    }

    @Test
    void shouldThrowWhenMainStackIsEmpty() {
        assertThrows(RuntimeException.class, () -> opNotIf.execute(context));
    }
}
