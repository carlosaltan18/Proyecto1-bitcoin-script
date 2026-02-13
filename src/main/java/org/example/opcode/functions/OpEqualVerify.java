package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;

import java.util.Arrays;

public class OpEqualVerify implements Opcode {


    @Override
    public void execute(ExecutionContext context) {
        if (context.getStack().size() < 2) {
            throw new RuntimeException("OP_EQUALVERIFY needs 2 items on the stack");
        }

        byte[] a = context.getStack().pop();
        byte[] b = context.getStack().pop();

        boolean equal = Arrays.equals(a, b);

        if (!equal){
            throw new RuntimeException("OPEQUAL_VERIFY Failed");
        }
    }
}