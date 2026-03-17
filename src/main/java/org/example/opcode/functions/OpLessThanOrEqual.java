package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;

/**
 * OP_LESSTHANOREQUAL pops two items and pushes 1 if the second-to-top is less than or equal to the top, 0 otherwise.
 */
public class OpLessThanOrEqual implements Opcode {

    @Override
    public void execute(ExecutionContext context) {
        var stack = context.getStack();
        if (stack.size() < 2) {
            throw new RuntimeException("OP_LESSTHANOREQUAL requires at least two elements on the stack");
        }
        int b = stack.pop()[0];
        int a = stack.pop()[0];
        stack.push(a <= b ? new byte[]{1} : new byte[]{0});
    }
}
