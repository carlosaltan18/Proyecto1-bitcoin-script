package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OpDropTest {

    private ExecutionContext context;
    private OpDrop opDrop;

    @BeforeEach
    public void setUp() {
        context = new ExecutionContext();
        opDrop = new OpDrop();
    }

    @Test
    void shouldRemoveTopElement() {
        var stack = context.getStack();

        stack.push(new byte[]{1});
        stack.push(new byte[]{2});

        opDrop.execute(context);

        assertEquals(1, stack.size());
        assertArrayEquals(new byte[]{1}, stack.pop());
    }

    @Test
    void shouldRemoveOnlyTopElement() {
        var stack = context.getStack();

        stack.push(new byte[]{5});
        stack.push(new byte[]{10});
        stack.push(new byte[]{15});

        opDrop.execute(context);

        assertEquals(2, stack.size());

        assertArrayEquals(new byte[]{10}, stack.pop());
        assertArrayEquals(new byte[]{5}, stack.pop());
    }

    @Test
    void shouldFailIfStackIsEmpty() {
        assertThrows(RuntimeException.class, () -> opDrop.execute(context));
    }

    @Test
    void shouldLeaveStackEmptyIfOnlyOneElement() {
        var stack = context.getStack();

        stack.push(new byte[]{9});

        opDrop.execute(context);

        assertEquals(0, stack.size());
    }
}
