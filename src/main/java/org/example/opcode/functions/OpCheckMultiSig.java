package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;

/**
 * Implements {@code OP_CHECKMULTISIG} — the m-of-n multisignature check.
 *
 * <p>Expected stack layout before execution (bottom → top):
 * <pre>
 *   OP_0 (dummy) | sig₁ … sigₘ | M | pub₁ … pubₙ | N
 * </pre>
 *
 * <p>The opcode pops N, then N public keys, then M (required signatures),
 * then M signatures, then the mandatory dummy element (an artifact of a
 * historical off-by-one bug in the original Bitcoin implementation).
 * If all counts are satisfied it pushes {@code 0x01} (true).
 *
 * <p><b>Note:</b> signature validation is simulated — any non-empty
 * byte array is accepted as a valid signature in this interpreter.
 */
public class OpCheckMultiSig implements Opcode {

    /**
     * Executes the multisig check.
     *
     * @param context the current execution context
     * @throws RuntimeException if the stack does not contain enough elements
     */
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
