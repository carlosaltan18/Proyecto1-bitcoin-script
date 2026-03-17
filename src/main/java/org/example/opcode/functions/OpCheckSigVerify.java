package org.example.opcode.functions;

import org.example.Crypto.CryptoMock;
import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;

/**
 * OP_CHECKSIGVERIFY combines OP_CHECKSIG and OP_VERIFY.
 * Verifies the signature and fails the script immediately if invalid.
 * Does not push a result onto the stack.
 */
public class OpCheckSigVerify implements Opcode {

    @Override
    public void execute(ExecutionContext context) {
        var stack = context.getStack();
        if (stack.size() < 2) {
            throw new RuntimeException("OP_CHECKSIGVERIFY requires at least two elements on the stack");
        }
        byte[] pubKey = stack.pop();
        byte[] sig = stack.pop();
        boolean valid = CryptoMock.checkSig(sig, pubKey);
        if (!valid) {
            throw new RuntimeException("OP_CHECKSIGVERIFY failed: invalid signature");
        }
    }
}
