package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpOverTest {

    private ExecutionContext context;
    private OpOver opOver;

    @BeforeEach
    void setUp() {
        context = new ExecutionContext();
        opOver = new OpOver();
    }

    @Test
    void shouldCopySecondElementToTop() {
        context.getStack().push(new byte[]{1}); // bottom
        context.getStack().push(new byte[]{2}); // top

        opOver.execute(context);

        // Stack should now be [1, 2, 1] with 1 on top
        assertArrayEquals(new byte[]{1}, context.getStack().pop());
        assertArrayEquals(new byte[]{2}, context.getStack().pop());
        assertArrayEquals(new byte[]{1}, context.getStack().pop());
    }

    @Test
    void shouldIncreaseStackSizeByOne() {
        context.getStack().push(new byte[]{1});
        context.getStack().push(new byte[]{2});

        opOver.execute(context);

        assertEquals(3, context.getStack().size());
    }

    @Test
    void shouldNotModifyOriginalSecondElement() {
        context.getStack().push(new byte[]{5});
        context.getStack().push(new byte[]{9});

        opOver.execute(context);

        context.getStack().pop(); // remove copy
        context.getStack().pop(); // remove top (9)
        assertArrayEquals(new byte[]{5}, context.getStack().pop()); // original second still there
    }

    @Test
    void shouldThrowWhenStackHasOnlyOneElement() {
        context.getStack().push(new byte[]{1});
        assertThrows(RuntimeException.class, () -> opOver.execute(context));
    }

    @Test
    void shouldThrowWhenStackIsEmpty() {
        assertThrows(RuntimeException.class, () -> opOver.execute(context));
    }
}
