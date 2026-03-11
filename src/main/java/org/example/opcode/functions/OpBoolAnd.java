package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;

/**
 * A class that implements OpBoolAnd to Operate the two last elements and do a operation And
 */
public class OpBoolAnd implements Opcode {

    /**
     * Execution an AND operation with two last items in the stack
     * @param context Context of the stack
     */
    @Override
    public void execute(ExecutionContext context) {

        if (context.getStack().size() < 2)
            throw new RuntimeException("OP_BOOLAND requires two elements");

        boolean b = context.getStack().pop()[0] != 0;
        boolean a = context.getStack().pop()[0] != 0;

        boolean result = a && b;

        context.getStack().push(new byte[]{(byte) (result ? 1 : 0)});
    }
}