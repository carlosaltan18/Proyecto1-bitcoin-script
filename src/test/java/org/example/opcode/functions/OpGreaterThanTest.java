package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpGreaterThanTest {

    private ExecutionContext context;
    private OpGreaterThan opGreaterThan;

    @BeforeEach
    void setUp() {
        context = new ExecutionContext();
        opGreaterThan = new OpGreaterThan();
    }

    @Test
    void shouldPushOneWhenFirstIsGreaterThanSecond() {
        // a=5, b=3 → 5 > 3 = true
        context.getStack().push(new byte[]{5});
        context.getStack().push(new byte[]{3});

        opGreaterThan.execute(context);

        assertArrayEquals(new byte[]{1}, context.getStack().pop());
    }

    @Test
    void shouldPushZeroWhenFirstIsLessThanSecond() {
        // a=3, b=5 → 3 > 5 = false
        context.getStack().push(new byte[]{3});
        context.getStack().push(new byte[]{5});

        opGreaterThan.execute(context);

        assertArrayEquals(new byte[]{0}, context.getStack().pop());
    }

    @Test
    void shouldPushZeroWhenValuesAreEqual() {
        context.getStack().push(new byte[]{4});
        context.getStack().push(new byte[]{4});

        opGreaterThan.execute(context);

        assertArrayEquals(new byte[]{0}, context.getStack().pop());
    }

    @Test
    void shouldThrowWhenStackHasFewerThanTwoElements() {
        context.getStack().push(new byte[]{1});
        assertThrows(RuntimeException.class, () -> opGreaterThan.execute(context));
    }

    @Test
    void shouldThrowWhenStackIsEmpty() {
        assertThrows(RuntimeException.class, () -> opGreaterThan.execute(context));
    }
}
