package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OpIfTest {
    private ExecutionContext context;
    private OpIf opIf;

    @BeforeEach
    public void setUp() {
        context = new ExecutionContext();
        opIf = new OpIf();
    }

    @Test
    public void shouldPushTrueToIfStackIfElementIsNotZero() {
        context.getStack().push(new byte[]{1});

        opIf.execute(context);

        assertEquals(0, context.getStack().size());
        assertFalse(context.getIfStack().isEmpty());
        assertTrue(context.getIfStack().peek());
    }

    @Test
    public void shouldPushFalseToIfStackIfElementIsZero() {
        context.getStack().push(new byte[]{0});

        opIf.execute(context);

        assertFalse(context.getIfStack().peek());
    }

    @Test
    public void shouldPushFalseIfArrayIsEmpty() {
        context.getStack().push(new byte[]{});

        opIf.execute(context);

        assertFalse(context.getIfStack().peek());
    }

    @Test
    public void shouldTreatNonOneValuesAsTrue() {
        context.getStack().push(new byte[]{5});

        opIf.execute(context);

        assertTrue(context.getIfStack().peek());
    }

    @Test
    public void shouldFailIfStackIsEmpty() {
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                opIf.execute(context)
        );

        assertEquals("OP_IF requires one element", exception.getMessage());
    }

    @Test
    public void shouldHandleMultipleIfsInIfStack() {