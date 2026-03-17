package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;
import org.example.opcode.helpers.ScriptUtils;

/**
 * OP_VERIFY pops the top item and fails the script if it is false (zero or empty).
 * Does not push anything back.
 */
public class OpVerify implements Opcode {

    @Override
    public void execute(ExecutionContext context) {
        var stack = context.getStack();
        if (stack.isEmpty()) {
            throw new RuntimeException("OP_VERIFY requires at least one element on the stack");
        }
        byte[] top = stack.pop();
        boolean truthy = ScriptUtils.isTruthy(top);
        if (!truthy) {
            throw new RuntimeException("OP_VERIFY failed: top of stack is false");
        }
    }
}
