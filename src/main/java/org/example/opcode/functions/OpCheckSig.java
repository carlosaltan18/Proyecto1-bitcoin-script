package org.example.opcode.functions;

import org.example.Crypto.CryptoMock;
import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;

/**
 * A class tha serves to validate the firm and key public
 */

public class OpCheckSig implements Opcode {
    /**
     *  execute the verify
     * @param context context of the stack
     */
    @Override
    public void execute(ExecutionContext context) {
        if (context.getStack().size() < 2){
            throw new RuntimeException("CHECKSIG: stack needs to have at least 2 elements");
        }

        byte[] pubKey = context.getStack().pop();
        byte[] sig = context.getStack().pop();

        boolean valid = CryptoMock.checkSig(sig, pubKey);

        context.getStack().push(valid ? new byte[]{1} : new byte[]{0});



    }
}
