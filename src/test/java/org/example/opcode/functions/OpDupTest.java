package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OpDupTest {
    private ExecutionContext context;
    private OpDup opDup;

    @BeforeEach
    public void setUp() {
        context = new ExecutionContext();
        opDup = new OpDup();
    }

    @Test
    public void shouldDuplicateTopElement() {
        context.getStack().push(new byte[]{10});

        opDup.execute(context);

        assertEquals(2, context.getStack().size());

        assertArrayEquals(new byte[]{10}, context.getStack().pop());
        assertArrayEquals(new byte[]{10}, context.getStack().pop());
    }

    @Test
    public void shouldNotAffectOtherElements() {
        context.getStack().push(new byte[]{5});
        context.getStack().push(new byte[]{20});

        opDup.execute(context);

        assertEquals(3, context.getStack().size());

        assertArrayEquals(new byte[]{20}, context.getStack().pop());
        assertArrayEquals(new byte[]{20}, context.getStack().pop());
        assertArrayEquals(new byte[]{5}, context.getStack().pop());
    }

    @Test
    public void shouldFailIfStackIsEmpty() {
        assertThrows(RuntimeException.class, () -> opDup.execute(context));
    }

    @Test
    public void duplicatedElementShouldBeIndependentCopy() {
        byte[] original = new byte[]{10};
        context.getStack().push(original);

        opDup.execute(context);

        byte[] first = context.getStack().pop();
        byte[] second = context.getStack().pop();

        first[0] = 20;

        assertNotEquals(first[0], second[0]);
    }
}
