package org.example.opcode.functions;

import org.example.opcode.Opcode;
import org.example.interpreter.ExecutionContext;

/**
 * A class that serves to add ths sumatory of the two last elements of the stack
 */
public class OpAdd implements Opcode {

    /**
     * Method that pop de las two last elements in the stack and sum thart
     * @param context Context of the stack
     */
    @Override
    public void execute(ExecutionContext context){
        if(context.getStack().size() < 2){
            throw new RuntimeException("OP_ADD requires two elements");
        }
        int b = context.getStack().pop()[0];
        int a = context.getStack().pop()[0];

        int result = a + b;
        context.getStack().push(new byte[]{(byte) result});
    }
}
