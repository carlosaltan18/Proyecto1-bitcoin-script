package org.example.stack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StackScriptTest {
    @Test
    void testPushIncreasesSize() {
        StackScript<byte[]> stack = new StackScript<>();
        stack.push(new byte[]{1});
        assertEquals(1, stack.size());
    }

    @Test
    void testPopReturnsLastElement() {
        StackScript<byte[]> stack = new StackScript<>();
        stack.push(new byte[]{1});
        stack.push(new byte[]{2});

        byte[] result = stack.pop();
        assertArrayEquals(new byte[]{2}, result);
    }

    @Test
    void testPeekReturnsTopWithoutRemoving() {
        StackScript<byte[]> stack = new StackScript<>();
        stack.push(new byte[]{5});

        byte[] result = stack.peek();

        assertArrayEquals(new byte[]{5}, result);
        assertEquals(1, stack.size());
    }

    @Test
    void testIsEmptyInitiallyTrue() {
        StackScript<byte[]> stack = new StackScript<>();
        assertTrue(stack.isEmpty());
    }

    @Test
    void testPopThrowsExceptionWhenEmpty() {
        StackScript<byte[]> stack = new StackScript<>();

        RuntimeException exception = assertThrows(RuntimeException.class, stack::pop);

        assertEquals("Stack underflow", exception.getMessage());
    }
}