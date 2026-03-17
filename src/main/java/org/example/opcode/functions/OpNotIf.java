package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;
import org.example.opcode.helpers.ExecState;
import org.example.opcode.helpers.ScriptUtils;

/**
 * OP_NOTIF is the inverse of OP_IF: executes the branch when the top of the stack is false (zero/empty).
 * If already in a skipped branch, pushes PARENT_NOT_EXECUTING without touching the main stack.
 */
public class OpNotIf implements Opcode {

    @Override
    public void execute(ExecutionContext context) {
        var execStack = context.getExecStack();

        if (!context.isExecuting()) {
            execStack.push(ExecState.PARENT_NOT_EXECUTING);
            return;
        }

        if (context.getStack().isEmpty()) {
            throw new RuntimeException("OP_NOTIF requires one element on the stack");
        }

        byte[] value = context.getStack().pop();
        boolean condition = ScriptUtils.isTruthy(value);
        execStack.push(condition ? ExecState.NOT_EXECUTING : ExecState.EXECUTING);
    }
}
