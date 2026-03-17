package org.example.opcode;

import org.example.interpreter.ExecutionContext;

/**
 * Contract for every Bitcoin Script opcode.
 *
 * <p>Each implementation encapsulates a single instruction in the Bitcoin
 * Script language. Opcodes operate on the {@link ExecutionContext} — they
 * pop arguments from the main stack, perform their logic, and push results
 * back. They may also interact with the exec stack for control-flow opcodes.
 *
 * <p>Implementations must throw {@link RuntimeException} on any error
 * (stack underflow, type mismatch, verification failure, etc.) so the
 * interpreter can catch it and mark the script as invalid.
 */
public interface Opcode {

    /**
     * Executes this opcode against the given execution context.
     *
     * @param context the current interpreter state (main stack, exec stack, trace flag)
     * @throws RuntimeException if execution fails for any reason
     */
    void execute(ExecutionContext context);
}
