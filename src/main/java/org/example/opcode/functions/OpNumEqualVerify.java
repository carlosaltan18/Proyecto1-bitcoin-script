package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;

/**
 * A class that implements OpNumEqualVerify to compare the two last elements
 * and verify they are equal.
 */
public class OpNumEqualVerify implements Opcode {
    /**
     * Executes an equality verification with the two last items in the stack.
     * If they are not equal, it terminates execution with an exception.
     * @param context Context of the stack
     */
    @Override
    public void execute(ExecutionContext context) {

        if (context.getStack().size() < 2)
            throw new RuntimeException("OP_NUMEQUALVERIFY requires two elements");

        int b = context.getStack().pop()[0];
        int a = context.getStack().pop()[0];

        if (a != b)
            throw new RuntimeException("OP_NUMEQUALVERIFY failed");
    }
}