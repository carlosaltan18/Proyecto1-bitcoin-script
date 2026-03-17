package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;

/**
 * OpEndIf ensures that an if block exists. It only
 * closes the current conditional frame.
 */

public class OpEndIf implements Opcode {
    @Override
    public void execute(ExecutionContext context) {
        var executionStack = context.getExecStack();

        if (executionStack.isEmpty()) {
            throw new RuntimeException("No matching IF condition");
        }

        // Close the current conditional frame
        executionStack.pop();
    }
}
