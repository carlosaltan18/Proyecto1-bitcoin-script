package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;

/**
 * A class that serves to negate an operation
 */
public class OpNot implements Opcode {

    /**
     * A methot that serves to pop the last item in the stack and negate
     * @param context Context Of the stack
     */
    @Override
    public void execute(ExecutionContext context) {

        if (context.getStack().size() < 1)
            throw new RuntimeException("OP_NOT requires one element");

        boolean value = context.getStack().pop()[0] != 0;

        context.getStack().push(new byte[]{(byte) (value ? 0 : 1)});
    }
}