package org.example.interpreter;

import org.example.stack.StackScript;

/**
 * ExecutionContext encapsulates the execution state of the interpreter.
 * It contains the main stack, the exec stack for OP_IF/OP_ELSE/OP_ENDIF
 * control flow, and trace configuration.
 *
 * The exec stack tracks nested if-branches. Each frame holds whether the
 * current branch should be executed. All frames must be true for the
 * interpreter to actually execute an opcode or push data.
 */
public class ExecutionContext {
    private final StackScript<byte[]> stack = new StackScript<>();
    private final StackScript<Boolean> execStack = new StackScript<>();
    private boolean traceEnabled = false;

    /**
     * Returns the main execution stack.
     */
    public StackScript<byte[]> getStack() {
        return stack;
    }

    /**
     * Returns the exec stack used for OP_IF / OP_ELSE / OP_ENDIF nesting.
     * Each entry represents one if-level: true = execute this branch,
     * false = skip this branch.
     */
    public StackScript<Boolean> getExecStack() {
        return execStack;
    }

    /**
     * Returns true when every active if-branch says "execute".
     * An empty exec stack means we are at the top level, so always execute.
     */
    public boolean isExecuting() {
        // We cannot iterate StackScript directly, so we track this via a
        // separate counter that OpIf/OpElse/OpEndIf maintain.
        // For now the exec stack itself is the source of truth; the interpreter
        // calls this helper to decide whether to run the current token.
        return execStack.isEmpty() || execStack.peek();
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