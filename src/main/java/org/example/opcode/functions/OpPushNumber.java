package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;


/**
 * Class for pushing a number 1 into a stack
 */
public class OpPushNumber implements Opcode {
    final private int number;

    public OpPushNumber(int number) {
        this.number = number;
    }

    @Override
    public void execute(ExecutionContext context) {
        //cast int number to byte :p
        context.getStack().push(new byte[]{(byte)number});
    }

}
