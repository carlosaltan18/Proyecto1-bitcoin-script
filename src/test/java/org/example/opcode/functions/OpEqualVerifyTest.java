package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpEqualVerifyTest {

    @Test
    void shouldPassWhenValuesAreEqual(){
        ExecutionContext context = new ExecutionContext();
        context.getStack().push("abc".getBytes());
        context.getStack().push("abc".getBytes());

        OpEqualVerify op = new OpEqualVerify();

            assertDoesNotThrow(() -> op.execute(context));
            assertEquals(0, context.getStack().size());

    }

}