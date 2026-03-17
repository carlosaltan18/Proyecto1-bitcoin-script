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
        var execStack = context.getExecStack();
        if (execStack.isEmpty()) throw new RuntimeException("OP_ENDIF sin OP_IF");

        execStack.pop();
    }
}
