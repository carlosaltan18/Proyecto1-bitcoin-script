package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;

/**
 * OP_RETURN immediately marks the script as invalid.
 * Used to embed arbitrary data in outputs (OP_RETURN outputs are unspendable).
 */
public class OpReturn implements Opcode {

    @Override
    public void execute(ExecutionContext context) {
        throw new RuntimeException("OP_RETURN: script terminated");
    }
}
