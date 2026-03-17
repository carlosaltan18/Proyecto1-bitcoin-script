package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpReturnTest {

    private ExecutionContext context;
    private OpReturn opReturn;

    @BeforeEach
    void setUp() {
        context = new ExecutionContext();
        opReturn = new OpReturn();
    }

    @Test
    void shouldAlwaysThrow() {
        assertThrows(RuntimeException.class, () -> opReturn.execute(context));
    }

    @Test
    void shouldThrowEvenWithDataOnStack() {
        context.getStack().push(new byte[]{1});
        assertThrows(RuntimeException.class, () -> opReturn.execute(context));
    }

    @Test
    void shouldThrowWithEmptyStack() {
        assertThrows(RuntimeException.class, () -> opReturn.execute(context));
    }

    @Test
    void shouldIncludeDescriptiveMessage() {
        RuntimeException ex = assertThrows(RuntimeException.class, () -> opReturn.execute(context));
        assertTrue(ex.getMessage().contains("OP_RETURN"));
    }
}
