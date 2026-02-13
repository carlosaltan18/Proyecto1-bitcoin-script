package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpCheckSigTest {
    @Test
    void shouldPushTrueWhenValid() {
        ExecutionContext context = new ExecutionContext();
        context.getStack().push("signature".getBytes());
        context.getStack().push("pubKey".getBytes());

        OpCheckSig op = new OpCheckSig();
        op.execute(context);

        byte[] result = context.getStack().pop();

        assertEquals(1, result[0]);
    }

    @Test
    void shouldPushFalseWhenInvalid() {
        ExecutionContext context = new ExecutionContext();
        context.getStack().push(new byte[]{}); //firma vacia
        context.getStack().push("pubKey".getBytes());

        OpCheckSig op = new OpCheckSig();
        op.execute(context);

        byte[] result = context.getStack().pop();

        assertEquals(0, result[0]);
    }

    @Test
    void shouldThrowWhenNotEnoughElements() {
        ExecutionContext context = new ExecutionContext();
        context.getStack().push("uno".getBytes());

        OpCheckSig op = new OpCheckSig();

        assertThrows(RuntimeException.class, () -> op.execute(context));
    }

}