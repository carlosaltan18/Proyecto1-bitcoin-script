package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpVerifyTest {

    private ExecutionContext context;
    private OpVerify opVerify;

    @BeforeEach
    void setUp() {
        context = new ExecutionContext();
        opVerify = new OpVerify();
    }

    @Test
    void shouldPassAndPopWhenTopIsTruthy() {
        context.getStack().push(new byte[]{1});
        opVerify.execute(context);
        assertTrue(context.getStack().isEmpty());
    }

    @Test
    void shouldPassWhenTopIsAnyNonZeroByte() {
        context.getStack().push(new byte[]{42});
        assertDoesNotThrow(() -> opVerify.execute(context));
    }

    @Test
    void shouldThrowWhenTopIsZero() {
        context.getStack().push(new byte[]{0});
        assertThrows(RuntimeException.class, () -> opVerify.execute(context));
    }

    @Test
    void shouldThrowWhenTopIsEmptyArray() {
        context.getStack().push(new byte[]{});
        assertThrows(RuntimeException.class, () -> opVerify.execute(context));
    }

    @Test
    void shouldThrowWhenStackIsEmpty() {
        assertThrows(RuntimeException.class, () -> opVerify.execute(context));
    }
}
