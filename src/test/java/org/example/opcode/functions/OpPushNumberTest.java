package org.example.opcode.functions;

import org.example.interpreter.ExecutionContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OpPushNumberTest {

    private ExecutionContext context;

    @BeforeEach
    public void setUp() {
        context = new ExecutionContext();
    }

    @Test
    void shouldPushSingleByteNumber() {
        OpPushNumber op = new OpPushNumber(5);

        op.execute(context);

        assertEquals(1, context.getStack().size());
        assertArrayEquals(new byte[]{5}, context.getStack().pop());
    }

    @Test
    void shouldPushZeroCorrectly() {
        OpPushNumber op = new OpPushNumber(0);

        op.execute(context);

        assertArrayEquals(new byte[]{0}, context.getStack().pop());
    }

    @Test
    void shouldPushDifferentNumbersIndependently() {
        OpPushNumber op1 = new OpPushNumber(3);
        OpPushNumber op2 = new OpPushNumber(7);

        op1.execute(context);
        op2.execute(context);

        assertEquals(2, context.getStack().size());

        assertArrayEquals(new byte[]{7}, context.getStack().pop());
        assertArrayEquals(new byte[]{3}, context.getStack().pop());
    }

    @Test
    void shouldMaintainStackOrder() {
        OpPushNumber op1 = new OpPushNumber(1);
        OpPushNumber op2 = new OpPushNumber(2);
        OpPushNumber op3 = new OpPushNumber(3);

        op1.execute(context);
        op2.execute(context);
        op3.execute(context);

        assertArrayEquals(new byte[]{3}, context.getStack().pop());
        assertArrayEquals(new byte[]{2}, context.getStack().pop());
        assertArrayEquals(new byte[]{1}, context.getStack().pop());
    }
}
