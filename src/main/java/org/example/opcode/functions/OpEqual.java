package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.example.opcode.Opcode;

import java.util.Arrays;

/**
 *  Compare (pop) the top two items on the stack and push 1 if they are equal, 0 otherwise.
 */
public class OpEqual implements Opcode {

    /**
     *
     * @param context context of the stack.
     * @throws RuntimeException if there is less than 2 elements in the stack.
     * returns 1 if the items are the same, returns 0 otherwise.
     */

    @Override
    public void execute(ExecutionContext context) {
        final var stack = context.getStack();

        if (stack.size() < 2) {
            throw new RuntimeException("Not enough arguments to apply OpEqual");
        }

        final var firstTop = stack.pop();
        final var secondTop = stack.pop();

        boolean itemsAreEqual = Arrays.equals(firstTop, secondTop);

        stack.push(itemsAreEqual ? new byte[]{1} : new byte[]{0});
        }

    }

