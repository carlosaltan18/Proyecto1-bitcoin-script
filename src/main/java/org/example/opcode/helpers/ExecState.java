package org.example.opcode.helpers;

/**
 * Represents the execution state of one level of a nested {@code OP_IF} block.
 *
 * <p>The interpreter maintains a stack of {@code ExecState} frames — one per
 * open {@code OP_IF} / {@code OP_NOTIF}. An opcode or data push is only
 * executed when every frame in the stack is {@link #EXECUTING}.
 */
public enum ExecState {
    /** This branch condition was true; opcodes inside should execute. */
    EXECUTING,
    /**
     * This branch condition was false; opcodes inside should be skipped.
     * Used after {@code OP_IF 0} or {@code OP_NOTIF 1}.
     */
    NOT_EXECUTING,
    /**
     * A parent {@code OP_IF} block is already skipping, so this nested
     * block is skipped regardless of its own condition.
     */
    PARENT_NOT_EXECUTING
}