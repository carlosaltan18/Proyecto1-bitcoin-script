package org.example.stack;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * class implement the stack Deque
 */
public class StackScript {
    private final Deque<byte[]> stack = new ArrayDeque<>();

    /**
     *Method to add a byte array to the stack
     * @param data byte array
     */
    public void  push(byte[] data) {
        stack.push(data);
    }

    /**
     * Method to delete an get the first element on the stack
     * @return byte[]
     */
    public byte[] pop() {
        if (stack.isEmpty()) {
            throw new RuntimeException("Stack underflow");
        }
        return stack.pop();
    }

    /**
     * Method to get the firts element in the stack
     * @return byte[]
     */
    public byte[] peek() {
        return stack.peek();
    }

    /**
     * Boolean method thaht returns if it is empty
     * @return
     */
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    /**
     * Method that retuns the size of the stack
     * @return
     */
    public int size() {
        return stack.size();
    }
}
