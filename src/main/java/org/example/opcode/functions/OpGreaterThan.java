package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;

/**
 * OP_GREATERTHAN pops two items and pushes 1 if the second-to-top is greater than the top, 0 otherwise.
 */
public class OpGreaterThan implements Opcode {

    @Override
    public void execute(ExecutionContext context) {
        var stack = context.getStack();
        if (stack.size() < 2) {
            throw new RuntimeException("OP_GREATERTHAN requires at least two elements on the stack");
        }
        int b = stack.pop()[0];
        int a = stack.pop()[0];
        stack.push(a > b ? new byte[]{1} : new byte[]{0});
    }
}
