package org.example.opcode.functions;

import org.example.Crypto.CryptoMock;
import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;

import java.util.zip.CheckedOutputStream;

/**
 * Class that serves to call a crypto mock
 */
public class OpHash160 implements Opcode {

    /**
     * method that serves to hash de token an push into stack context
     * @param context
     */
    @Override
    public void execute(ExecutionContext context) {
        if (context.getStack().isEmpty() ){
            throw new RuntimeException("OP_HASH160: Stack cannot be empty");
        }

        byte[] data = context.getStack().pop();
        byte[]hashed = CryptoMock.hash160(data);

        context.getStack().push(hashed);
    }


}
