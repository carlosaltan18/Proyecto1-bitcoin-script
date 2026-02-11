package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;

/**
 * Class that serves to push zero into the stack
 */
public class OpPushZero implements Opcode {
    /**
     * Override funciont for push zero into the stack
     * @param context Context of the stack
     */
    @Override
    public void execute(ExecutionContext context){
        context.getStack().push(new byte[0]);
    }
}
