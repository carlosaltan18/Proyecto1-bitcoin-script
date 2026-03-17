package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;

/**
 * OP_SWAP swaps the top two items on the stack.
 */
public class OpSwap implements Opcode {

    @Override
    public void execute(ExecutionContext context) {
        var stack = context.getStack();
        if (stack.size() < 2) {
            throw new RuntimeException("OP_SWAP requires at least two elements on the stack");
        }
        byte[] top = stack.pop();
        byte[] second = stack.pop();
        stack.push(top);
        stack.push(second);
    }
}
