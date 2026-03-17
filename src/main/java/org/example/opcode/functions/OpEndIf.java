package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;

/**
 * Implements {@code OP_ENDIF} — closes the innermost open conditional block.
 *
 * <p>Pops one frame from the exec stack. Every {@code OP_IF} or {@code OP_NOTIF}
 * must have a matching {@code OP_ENDIF}; the interpreter validates this at the
 * end of script execution.
 */
public class OpEndIf implements Opcode {

    /**
     * Pops the innermost exec-stack frame.
     *
     * @param context the current execution context
     * @throws RuntimeException if there is no open {@code OP_IF} to close
     */
    @Override
    public void execute(ExecutionContext context) {
        var execStack = context.getExecStack();
        if (execStack.isEmpty()) throw new RuntimeException("OP_ENDIF sin OP_IF");

        execStack.pop();
    }
}
