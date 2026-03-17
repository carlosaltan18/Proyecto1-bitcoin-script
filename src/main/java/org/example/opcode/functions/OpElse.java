package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;
import org.example.opcode.helpers.ExecState;

/**
 * Op_Else allows for alternative execution paths
 * depending on conditions evaluated by preceding OP_IF
 */
public class OpElse implements Opcode {

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
