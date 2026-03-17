package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;
import org.example.opcode.helpers.ExecState;

/**
 * Implements {@code OP_ELSE} — flips the current exec-stack frame.
 *
 * <p>If the current frame is {@link ExecState#EXECUTING}, it becomes
 * {@link ExecState#NOT_EXECUTING} and vice versa. If the parent was
 * already skipping ({@link ExecState#PARENT_NOT_EXECUTING}), the state
 * is preserved unchanged so the whole inner block stays skipped.
 */
public class OpElse implements Opcode {

    /**
     * Flips the active branch in the exec stack.
     *
     * @param context the current execution context
     * @throws RuntimeException if there is no open {@code OP_IF} on the exec stack
     */
    @Override
    public void execute(ExecutionContext context) {
        var execStack = context.getExecStack();
        if (execStack.isEmpty()) throw new RuntimeException("OP_ELSE sin OP_IF");

        ExecState currentState = execStack.pop();

        if (currentState == ExecState.PARENT_NOT_EXECUTING) {
            execStack.push(ExecState.PARENT_NOT_EXECUTING);
        } else {
            execStack.push(currentState == ExecState.EXECUTING ?
                    ExecState.NOT_EXECUTING : ExecState.EXECUTING);
        }
    }
}
