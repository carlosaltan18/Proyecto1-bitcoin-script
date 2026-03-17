package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;

import java.util.Arrays;

/**
 * OP_OVER copies the second-to-top item and pushes it onto the top of the stack.
 * Stack before: [a, b] (b on top) → Stack after: [a, b, a]
 */
public class OpOver implements Opcode {

    @Override
    public void execute(ExecutionContext context) {
        var stack = context.getStack();
        if (stack.size() < 2) {
            throw new RuntimeException("OP_OVER requires at least two elements on the stack");
        }
        byte[] top = stack.pop();
        byte[] second = stack.peek();
        stack.push(top);
        stack.push(Arrays.copyOf(second, second.length));
    }
}
