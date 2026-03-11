package org.example.interpreter;

import org.example.stack.StackScript;

import java.util.Stack;

/**
 * ExecutionContext encapsulates the execution state of the interpreter.
 * It contains the main stack, control flow state, and trace configuration.
 */
public class ExecutionContext {
    private final StackScript<byte[]> stack = new StackScript<>();
    private final Stack<Boolean> ifStack = new Stack<>();
    private boolean traceEnabled = false;

    /**
     * Returns the main execution stack.
     */
    public StackScript<byte[]> getStack() {
        return stack;
    }

    /**
     * Returns the control flow stack.
     */
    public Stack<Boolean> getIfStack() {
        return ifStack;
    }

    /**
     * Enables or disables trace mode.
     */
    public void setTrace(boolean traceEnabled) {
        this.traceEnabled = traceEnabled;
    }

    /**
     * Returns whether trace mode is enabled.
     */
    public boolean isTraceEnabled() {
        return traceEnabled;
    }

    /**
     * Prints the stack if trace mode is active.
     */
    public void printStack() {
        if (traceEnabled) {
            System.out.println("Current Stack Size: " + stack.size());
        }
    }
}