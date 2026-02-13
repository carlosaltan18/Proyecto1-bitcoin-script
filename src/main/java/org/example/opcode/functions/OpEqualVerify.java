package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;

import java.util.Arrays;

/**
 * OpEqualVerify compares the top two items on the stack and removes them.
 * If they are equal, the script continues execution. If they are not equal, the script fails immediately.
 * @see OpEqual
 * OpEqualVerify combines the functionality of OP_EQUAL and OP_VERIFY (in v1, OP_VERIFY is not implemented yet)
 */
public class OpEqualVerify implements Opcode {

    @Override
    public void execute(ExecutionContext context) {
        final var stack = context.getStack();

        if (stack.size() < 2) {
            throw new RuntimeException("Not enough arguments to apply OpEqualVerify");
        }

        final var firstTop = stack.pop();
        final var secondTop = stack.pop();

        boolean itemsAreEqual = Arrays.equals(firstTop, secondTop);

        if (!itemsAreEqual) {
            throw new RuntimeException("Top two stack items are not equal");
        }

    }
}
