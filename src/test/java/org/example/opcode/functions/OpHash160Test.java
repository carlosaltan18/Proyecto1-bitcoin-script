package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpHash160Test {

    void shouldHashData() {
        ExecutionContext context = new ExecutionContext();
        context.getStack().push("abcdef".getBytes());

        OpHash160 op = new OpHash160();
        op.execute(context);

        byte[] result = context.getStack().pop();

        assertEquals(4, result.length); //cryptoMock devuelve 4 bytes
    }

    @Test
    void shouldThrowWhenStackEmpty() {
        ExecutionContext context = new ExecutionContext();

        OpHash160 op = new OpHash160();

        assertThrows(RuntimeException.class, () -> op.execute(context));
    }

}