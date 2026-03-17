package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;

/**
 * Op_Else allows for alternative execution paths
 * depending on conditions evaluated by preceding OP_IF
 */
public class OpElse implements Opcode {

    @Override
    public void execute(ExecutionContext context) {
        var executionStack = context.getExecStack();

    /*
      Verify there's an active IF
     */
        if (executionStack.isEmpty()) {
            throw new RuntimeException("OP_ELSE needs an IF condition");
        }

        var currentState = executionStack.peek();

    /*
      Invert the actual state
     */
        executionStack.pop();
        executionStack.push(!currentState);

    }
}
