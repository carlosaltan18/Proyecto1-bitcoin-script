package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;
import org.example.opcode.helpers.ExecState;

/**
 * OP_IF pops the top of the main stack and pushes a frame onto the exec stack.
 * If we are already inside a skipped branch, no pop happens and the new frame
 * is false, so the inner block is also skipped.
 */
public class OpIf implements Opcode {

    @Override
    public void execute(ExecutionContext context) {
        var execStack = context.getExecStack();

        if (!context.isExecuting()) {
            execStack.push(ExecState.PARENT_NOT_EXECUTING);
            return;
        }

        if (context.getStack().isEmpty()) {
            throw new RuntimeException("OP_IF requiere un elemento en el stack");
        }
        byte[] value = context.getStack().pop();
        boolean condition = value.length > 0 && value[0] != 0;

        execStack.push(condition ? ExecState.EXECUTING : ExecState.NOT_EXECUTING);
    }
}
