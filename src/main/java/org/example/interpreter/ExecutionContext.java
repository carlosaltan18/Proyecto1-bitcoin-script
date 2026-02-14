package org.example.interpreter;

import org.example.stack.StackScript;

/**
 * Class that serves to wrap the stack
 */
public class ExecutionContext {
    private final StackScript<byte[]> stack = new StackScript<>();

    public StackScript<byte[]> getStack() {
        return stack;
    }
}
