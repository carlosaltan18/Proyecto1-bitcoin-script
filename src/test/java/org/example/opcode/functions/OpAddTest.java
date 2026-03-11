package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OpAddTest {
    private ExecutionContext context;
    private OpAdd opAdd;

    @BeforeEach
    public void setUp() {
        context = new ExecutionContext();
        opAdd = new OpAdd();
    }

    @Test
    public void shouldAddTwoElementsCorrectly() {
        context.getStack().push(new byte[]{10});
        context.getStack().push(new byte[]{20});

        opAdd.execute(context);

        assertEquals(1, context.getStack().size());
        assertArrayEquals(new byte[]{30}, context.getStack().pop());
    }

    @Test
    public void shouldHandleNegativeSum() {
        context.getStack().push(new byte[]{10});
        context.getStack().push(new byte[]{-15});

        opAdd.execute(context);

        assertArrayEquals(new byte[]{-5}, context.getStack().pop());
    }

    @Test
    public void shouldFailIfStackHasLessThanTwoElements() {
        assertThrows(RuntimeException.class, () -> opAdd.execute(context));

        context.getStack().push(new byte[]{5});
        assertThrows(RuntimeException.class, () -> opAdd.execute(context));
    }

    @Test
    public void resultShouldBeANewIndependentByteArray() {
        context.getStack().push(new byte[]{1});
        context.getStack().push(new byte[]{2});

        opAdd.execute(context);

        byte[] result = context.getStack().pop();

        assertEquals(3, result[0]);
        result[0] = 100;
        assertNotEquals(3, result[0]);
    }

    @Test
    public void shouldHandleByteOverflow() {
        context.getStack().push(new byte[]{120});
        context.getStack().push(new byte[]{10});

        opAdd.execute(context);

        byte result = context.getStack().pop()[0];
        assertEquals((byte) 130, result);
    }
}