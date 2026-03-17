package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;

/**
 * OP_HASH256 — mock implementation.
 * Pops the top element and pushes back the first 4 bytes as a simulated double-SHA256 hash.
 * Real implementation to be provided by the professor.
 */
public class OpHash256 implements Opcode {

    @Override
    public void execute(ExecutionContext context) {
        var stack = context.getStack();
        if (stack.isEmpty()) {
            throw new RuntimeException("OP_HASH256 requires at least one element on the stack");
        }
        byte[] data = stack.pop();
        byte[] hash = java.util.Arrays.copyOfRange(data, 0, Math.min(4, data.length));
        stack.push(hash);
    }
}
