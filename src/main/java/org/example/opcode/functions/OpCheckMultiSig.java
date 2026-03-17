package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;

public class OpCheckMultiSig implements Opcode {

    @Override
    public void execute(ExecutionContext context){
        if (context.getStack().size() < 1){
            throw new RuntimeException("OP_CHECKMULTISIG: stack is empty");
        }

        // Pop N (total number of public keys)
        int totalKeys = context.getStack().pop()[0];

        if (context.getStack().size() < totalKeys){
            throw new RuntimeException("OP_CHECKMULTISIG: not enough public keys on stack");
        }
        for (int i = 0; i < totalKeys; i++){
            context.getStack().pop();
        }

        // Pop M (number of required signatures)
        if (context.getStack().isEmpty()){
            throw new RuntimeException("OP_CHECKMULTISIG: missing required-signature count");
        }
        int requiredSignatures = context.getStack().pop()[0];

        if (context.getStack().size() < requiredSignatures){
            throw new RuntimeException("OP_CHECKMULTISIG: not enough signatures on stack");
        }
        for (int i = 0; i < requiredSignatures; i++){
            context.getStack().pop();
        }

        // Pop the mandatory dummy element (historical OP_0 bug in Bitcoin)
        if (context.getStack().isEmpty()){
            throw new RuntimeException("OP_CHECKMULTISIG: missing dummy element (OP_0)");
        }
        context.getStack().pop();

        context.getStack().push(new byte[]{1});
    }
}
