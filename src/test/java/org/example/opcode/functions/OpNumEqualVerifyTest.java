package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OpNumEqualVerifyTest {
    private ExecutionContext context;
    private OpNumEqualVerify opNumEqualVerify;

    @BeforeEach
    public void setUp() {
        context = new ExecutionContext();
        opNumEqualVerify = new OpNumEqualVerify();
    }

    @Test
    public void shouldPassIfElementsAreEqual() {
        context.getStack().push(new byte[]{10});
        context.getStack().push(new byte[]{10});

        assertDoesNotThrow(() -> opNumEqualVerify.execute(context));

        assertEquals(0, context.getStack().size());
    }

    @Test
    public void shouldFailIfElementsAreNotEqual() {
        context.getStack().push(new byte[]{10});
        context.getStack().push(new byte[]{20});

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                opNumEqualVerify.execute(context)
        );

        assertEquals("OP_NUMEQUALVERIFY failed", exception.getMessage());
    }

    @Test
    public void shouldFailIfStackHasInsufficientElements() {
        context.getStack().push(new byte[]{5});

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                opNumEqualVerify.execute(context)
        );

        assertEquals("OP_NUMEQUALVERIFY requires two elements", exception.getMessage());
    }

    @Test
    public void shouldMaintainStackIntegrityForOtherElements() {
        context.getStack().push(new byte[]{99});
        context.getStack().push(new byte[]{7});
        context.getStack().push(new byte[]{7});

        opNumEqualVerify.execute(context);

        assertEquals(1, context.getStack().size());
        assertArrayEquals(new byte[]{99}, context.getStack().pop());
    }
}