package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;

/**
 * Class that serves to rest the two last elements in the stack
 */
public class OpSub implements Opcode {

    /**
     * Method that pop de las two last elements in the stack and rest
     * @param context Context of the stack
     */
    @Override
    public void execute(ExecutionContext context){
        if(context.getStack().size() < 2){
            throw new RuntimeException("OP_SUB requires two elements");
        }
        int b = context.getStack().pop()[0];
        int a = context.getStack().pop()[0];

        int result = a - b;
        context.getStack().push(new byte[]{(byte) result});
    }
}