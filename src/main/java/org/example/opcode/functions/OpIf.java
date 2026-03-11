package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;

/**
 * Class that serves to evaluate that the the top of the stack is different of 0
 */
public class OpIf implements Opcode {

    /**
     * Method that serves to evaluate if the top is different of 0
     * @param context Context of the stack
     */
    @Override
    public void execute(ExecutionContext context){
        if(context.getStack().size() < 1){
            throw new RuntimeException("OP_IF requires one element");
        }
        byte [] value = context.getStack().pop();
        boolean result = value.length > 0 && value[0] != 0;
        context.getIfStack().push(result);

    }
}
