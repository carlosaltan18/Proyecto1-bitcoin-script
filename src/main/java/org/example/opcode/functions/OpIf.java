package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;

/**
 * OP_IF pops the top of the main stack and pushes a frame onto the exec stack.
 * If we are already inside a skipped branch, no pop happens and the new frame
 * is false, so the inner block is also skipped.
 */
public class OpIf implements Opcode {

    @Override
    public void execute(ExecutionContext context) {
        if (!context.isExecuting()) {
            // We are in a skipped branch — nest another false frame without
            // touching the main stack.
            context.getExecStack().push(false);
            return;
        }

        if (context.getStack().size() < 1) {
            throw new RuntimeException("OP_IF requires one element on the stack");
        }

        byte[] value = context.getStack().pop();
        boolean condition = value.length > 0 && value[0] != 0;
        context.getExecStack().push(condition);
    }
}
