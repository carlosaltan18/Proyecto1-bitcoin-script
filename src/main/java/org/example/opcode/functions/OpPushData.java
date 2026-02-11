package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;

/**
 * Class that serves to push a data into stack
 */

public class OpPushData implements Opcode {
    private final byte[] data;
    public OpPushData(byte[] data) {
        this.data = data;
    }

    /**
     * Override the function execute for implement the push of a data
     * @param context Context of the stack
     */
    @Override
    public void execute(ExecutionContext context) {
        context.getStack().push(data);
    }

}
