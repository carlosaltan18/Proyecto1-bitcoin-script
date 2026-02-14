package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;

import java.util.Arrays;

/**
 * @author Daniel Vásquez
 * @see Opcode for the Overrided method.
 * OpDup duplicates the top element of the stack
 */
public class OpDup implements Opcode {

    /**
     * Override the method execute() to duplicate the top element into the stack
     * @param context Context of the stack
     * @throws RuntimeException if the stack is empty
     */
    @Override
    public void execute(ExecutionContext context) {
        var stack = context.getStack();

        var top = stack.peek();

        if (stack.isEmpty()) {
            throw new RuntimeException("OpDup is not applicable to an empty stack");
        }

        var copy = Arrays.copyOf(top, top.length);

        stack.push(copy);
    }
}
