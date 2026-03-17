package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;
import org.example.opcode.helpers.ExecState;
import org.example.opcode.helpers.ScriptUtils;

/**
 * OP_IF pops the top of the main stack and pushes a frame onto the exec stack.
 * If we are already inside a skipped branch, no pop happens and the new frame
 * is false, so the inner block is also skipped.
 */
public class OpIf implements Opcode {

    /**
     * Pops the top of the main stack and pushes a new frame onto the exec stack.
     * If already inside a skipped branch, pushes {@link ExecState#PARENT_NOT_EXECUTING}
     * without touching the main stack.
     *
     * @param context the current execution context
     * @throws RuntimeException if the stack is empty when the condition needs to be read
     */
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
        boolean condition = ScriptUtils.isTruthy(value);

        execStack.push(condition ? ExecState.EXECUTING : ExecState.NOT_EXECUTING);
    }
}
