package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;

/**
 * OpDrop is used to remove the top item from the stack,
 * with the second item becoming the new top item.
 */
public class OpDrop implements Opcode {
    @Override
    public void execute(ExecutionContext context) {

        var stack = context.getStack();

        stack.pop();
    }
}
