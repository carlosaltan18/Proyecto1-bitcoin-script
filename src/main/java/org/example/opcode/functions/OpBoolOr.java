package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;

/**
 * A class that implements OpCode and serves for do a Or operation
 */
public class OpBoolOr implements Opcode {

    /**
     * Do a Or operation with the two last items in te stack
     * @param context Context of the stack
     */
    @Override
    public void execute(ExecutionContext context) {

        if (context.getStack().size() < 2)
            throw new RuntimeException("OP_BOOLOR requires two elements");

        boolean b = context.getStack().pop()[0] != 0;
        boolean a = context.getStack().pop()[0] != 0;

        boolean result = a || b;

        context.getStack().push(new byte[]{(byte) (result ? 1 : 0)});
    }
}