package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpLessThanTest {

    private ExecutionContext context;
    private OpLessThan opLessThan;

    @BeforeEach
    void setUp() {
        context = new ExecutionContext();
        opLessThan = new OpLessThan();
    }

    @Test
    void shouldPushOneWhenFirstIsLessThanSecond() {
        // a=3, b=5 → push a first, then b on top → pop gives b=5, then a=3 → 3 < 5 = true
        context.getStack().push(new byte[]{3});
        context.getStack().push(new byte[]{5});

        opLessThan.execute(context);

        assertArrayEquals(new byte[]{1}, context.getStack().pop());
    }

    @Test
    void shouldPushZeroWhenFirstIsGreaterThanSecond() {
        // a=5, b=3 → 5 < 3 = false
        context.getStack().push(new byte[]{5});
        context.getStack().push(new byte[]{3});

        opLessThan.execute(context);

        assertArrayEquals(new byte[]{0}, context.getStack().pop());
    }

    @Test
    void shouldPushZeroWhenValuesAreEqual() {
        context.getStack().push(new byte[]{4});
        context.getStack().push(new byte[]{4});

        opLessThan.execute(context);

        assertArrayEquals(new byte[]{0}, context.getStack().pop());
    }

    @Test
    void shouldThrowWhenStackHasFewerThanTwoElements() {
        context.getStack().push(new byte[]{1});
        assertThrows(RuntimeException.class, () -> opLessThan.execute(context));
    }

    @Test
    void shouldThrowWhenStackIsEmpty() {
        assertThrows(RuntimeException.class, () -> opLessThan.execute(context));
    }
}
