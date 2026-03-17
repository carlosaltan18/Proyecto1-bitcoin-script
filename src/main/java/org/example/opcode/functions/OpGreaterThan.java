package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;

/**
 * OP_GREATERTHAN pops two items and pushes 1 if the second-to-top is greater than the top, 0 otherwise.
 */
public class OpGreaterThan implements Opcode {

    /**
     * Pops {@code b} (top) and {@code a} (second), pushes {@code 1} if {@code a > b}, else {@code 0}.
     *
     * @param context the current execution context
     * @throws RuntimeException if the stack has fewer than two elements
     */
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
