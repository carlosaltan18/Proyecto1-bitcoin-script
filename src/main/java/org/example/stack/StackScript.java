package org.example.stack;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * class implement the stack Deque
 */
public class StackScript<T> implements Iterable<T> {
    private final Deque<T> stack = new ArrayDeque<>();

    /**
     *Method to add a byte array to the stack
     * @param data byte array
     */
    public void  push(T data) {
        stack.push(data);
    }

    /**
     * Method to delete an get the first element on the stack
     * @return byte[]
     */
    public T pop() {
        if (stack.isEmpty()) {
            throw new RuntimeException("Stack underflow");
        }
        return stack.pop();
    }

    /**
     * Method to get the firts element in the stack
     * @return byte[]
     */
    public T peek() {
        if (stack.isEmpty()) {
            throw new RuntimeException("Stack underflow");
        }
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

    /**
     * Iterates from top to bottom (head-first), matching visual stack order.
     */
    @Override
    public Iterator<T> iterator() {
        return stack.iterator();
    }
}
