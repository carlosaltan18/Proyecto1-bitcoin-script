package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpGreaterThanOrEqualTest {

    private ExecutionContext context;
    private OpGreaterThanOrEqual op;

    @BeforeEach
    void setUp() {
        context = new ExecutionContext();
        op = new OpGreaterThanOrEqual();
    }

    @Test
    void shouldPushOneWhenFirstIsGreaterThanSecond() {
        // a=5, b=3 → 5 >= 3 = true
        context.getStack().push(new byte[]{5});
        context.getStack().push(new byte[]{3});
        op.execute(context);
        assertArrayEquals(new byte[]{1}, context.getStack().pop());
    }

    @Test
    void shouldPushOneWhenValuesAreEqual() {
        context.getStack().push(new byte[]{4});
        context.getStack().push(new byte[]{4});
        op.execute(context);
        assertArrayEquals(new byte[]{1}, context.getStack().pop());
    }

    @Test
    void shouldPushZeroWhenFirstIsLessThanSecond() {
        // a=3, b=5 → 3 >= 5 = false
        context.getStack().push(new byte[]{3});
        context.getStack().push(new byte[]{5});
        op.execute(context);
        assertArrayEquals(new byte[]{0}, context.getStack().pop());
    }

    @Test
    void shouldThrowWhenStackHasFewerThanTwoElements() {
        context.getStack().push(new byte[]{1});
        assertThrows(RuntimeException.class, () -> op.execute(context));
    }

    @Test
    void shouldThrowWhenStackIsEmpty() {
        assertThrows(RuntimeException.class, () -> op.execute(context));
    }
}
