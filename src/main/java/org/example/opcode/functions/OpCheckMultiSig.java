package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;

public class OpCheckMultiSig implements Opcode {

    @Override
    public void execute(ExecutionContext context){
            if (context.getStack().size() < 1){
                throw new RuntimeException("OpCheckMultiSig needs at least 1 element in the stack");
            }
            int totalkeys = context.getStack().pop()[0];

            if (context.getStack().size() < totalkeys){
                throw new RuntimeException("Not enough public keys");
            }
            for (int i = 0; i < totalkeys; i++){
                context.getStack().pop();
            }

            int requiredSignatures = context.getStack().pop()[0];

            if (context.getStack().size() < requiredSignatures){
                throw new RuntimeException("not enough signatures");
            }
            for (int i = 0; i < requiredSignatures; i++){
                context.getStack().pop();
            }
            context.getStack().push(new byte[]{1});
    }
}
