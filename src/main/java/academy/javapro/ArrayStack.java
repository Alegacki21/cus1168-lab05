package academy.javapro;

/**
 * Array-based implementation of the CustomStack interface.
 * @param <T> the type of elements in the stack
 */
public class ArrayStack<T extends Number> implements CustomStack<T> {
    // Constants for stack configuration
    private static final int DEFAULT_CAPACITY = 10;
    private static final double GROWTH_FACTOR = 1.5;

    // Static variables for tracking across all instances
    private static int totalStacks = 0;
    private static int totalElements = 0;

    // Instance variables
    private Object[] elements; // Using Object[] since generic arrays aren't directly supported
    private int top; // Index of the top element
    private int operationCount; // Tracks operations on this stack
    private final int stackId; // Unique identifier for this stack instance

    /**
     * Creates a new ArrayStack with default capacity.
     */
    public ArrayStack() {
        elements = new Object[DEFAULT_CAPACITY];
        top = -1;
        operationCount = 0;
        stackId = ++totalStacks;
    }

    /**
     * Adds an element to the top of the stack.
     * @param element the element to add
     */
    @Override
    public void push(T element) {
        operationCount++;
        if (top == elements.length - 1) {
            resize();
        }
        elements[++top] = element;
        totalElements++;
    }

    /**
     * Removes and returns the top element from the stack.
     * @return the top element, or null if the stack is empty
     */
    @SuppressWarnings("unchecked")
    @Override
    public T pop() {
        operationCount++;
        if (isEmpty()) {
            return null;
        }
        T popped = (T) elements[top];
        elements[top--] = null;
        totalElements--;
        return popped;
    }

    /**
     * Returns but does not remove the top element from the stack.
     * @return the top element, or null if the stack is empty
     */
    @SuppressWarnings("unchecked")
    @Override
    public T peek() {
        operationCount++;
        if (isEmpty()) {
            return null;
        }
        return (T) elements[top];
    }

    /**
     * Checks if the stack is empty.
     * @return true if the stack is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        operationCount++;
        return top == -1;
    }

    /**
     * Returns the number of elements in the stack.
     * @return the number of elements
     */
    @Override
    public int size() {
        operationCount++;
        return top + 1;
    }

    /**
     * Resizes the array when it becomes full.
     */
    private void resize() {
        int newCapacity = (int) (elements.length * GROWTH_FACTOR);
        Object[] newArray = new Object[newCapacity];
        System.arraycopy(elements, 0, newArray, 0, elements.length);
        elements = newArray;
    }

    /**
     * Adds the top two elements and pushes the result back onto the stack.
     * Works only for numeric types.
     */
    public void addTopTwo() {
        if (size() < 2) {
            System.out.println("Error: Not enough elements to perform addition.");
            return;
        }
        Number first = pop();
        Number second = pop();
        Number result;
        if (first instanceof Integer && second instanceof Integer) {
            result = first.intValue() + second.intValue();
        } else {
            result = first.doubleValue() + second.doubleValue();
        }
        push((T) result);
    }

    /**
     * Gets statistics for this stack instance.
     */
    public String getStats() {
        return "Stack #" + stackId + ": Size=" + size() + ", Operations=" + operationCount;
    }

    /**
     * Gets statistics across all ArrayStack instances.
     */
    public static String getGlobalStats() {
        return "Total stacks: " + totalStacks + ", Total elements: " + totalElements;
    }
}