package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for OP_CHECKMULTISIG.
 *
 * Bitcoin stack layout before the opcode runs (bottom → top):
 *   OP_0 (dummy) | sig1 ... sigM | M | pub1 ... pubN | N
 *
 * Pop order: N → pubkeys (×N) → M → sigs (×M) → dummy OP_0
 */
class OpCheckMultiSigTest {

    private ExecutionContext context;
    private OpCheckMultiSig op;

    @BeforeEach
    void setUp() {
        context = new ExecutionContext();
        op = new OpCheckMultiSig();
    }

    // ---------------------------------------------------------------
    // Helper: push a standard 2-of-3 multisig stack
    //   bottom: dummy | sig1 | sig2 | 2 | pub1 | pub2 | pub3 | 3 :top
    // ---------------------------------------------------------------
    private void push2of3() {
        context.getStack().push(new byte[]{0});            // dummy OP_0
        context.getStack().push("sig1".getBytes());
        context.getStack().push("sig2".getBytes());
        context.getStack().push(new byte[]{2});            // M = required sigs
        context.getStack().push("pub1".getBytes());
        context.getStack().push("pub2".getBytes());
        context.getStack().push("pub3".getBytes());
        context.getStack().push(new byte[]{3});            // N = total keys
    }

    @Test
    void shouldPushOneForValid2of3() {
        push2of3();
        op.execute(context);
        assertArrayEquals(new byte[]{1}, context.getStack().pop());
        assertTrue(context.getStack().isEmpty());
    }

    @Test
    void shouldPushOneFor1of1() {
        context.getStack().push(new byte[]{0});            // dummy
        context.getStack().push("sig1".getBytes());
        context.getStack().push(new byte[]{1});            // M = 1
        context.getStack().push("pub1".getBytes());
        context.getStack().push(new byte[]{1});            // N = 1

        op.execute(context);
        assertArrayEquals(new byte[]{1}, context.getStack().pop());
    }

    @Test
    void shouldLeaveStackCleanAfterExecution() {
        push2of3();
        op.execute(context);
        // only the result byte should remain
        assertEquals(1, context.getStack().size());
    }

    @Test
    void shouldThrowWhenStackIsEmpty() {
        assertThrows(RuntimeException.class, () -> op.execute(context));
    }

    @Test
    void shouldThrowWhenNotEnoughPublicKeys() {
        // N=3 but only 1 pub key present
        context.getStack().push(new byte[]{0});
        context.getStack().push("sig1".getBytes());
        context.getStack().push(new byte[]{1});
        context.getStack().push("pub1".getBytes());
        context.getStack().push(new byte[]{3}); // claims 3 keys, only 1 pushed

        assertThrows(RuntimeException.class, () -> op.execute(context));
    }

    @Test
    void shouldThrowWhenNotEnoughSignatures() {
        // M=3 but only 1 signature present
        context.getStack().push(new byte[]{0});
        context.getStack().push("sig1".getBytes());
        context.getStack().push(new byte[]{3}); // claims 3 required sigs
        context.getStack().push("pub1".getBytes());
        context.getStack().push(new byte[]{1});

        assertThrows(RuntimeException.class, () -> op.execute(context));
    }
}
