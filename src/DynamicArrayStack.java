/*
 * infix-to-postfix
 * github.com/01mu/infix-to-postfix
 */

public class DynamicArrayStack<AnyType> implements Stack<AnyType>
{
    public static final int DEFAULT_CAPACITY = 256;
    AnyType[] data;
    int topOfStack;

    public DynamicArrayStack()
    {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public DynamicArrayStack(int capacity)
    {
        topOfStack = -1;

        data = (AnyType[]) new Object[capacity];
    }

    public int size()
    {
        return topOfStack + 1;
    }

    public boolean isEmpty()
    {
        boolean empty = false;

        if(topOfStack == -1) {
            empty = true;
        }

        return empty;
    }

    public void push(AnyType newValue)
    {
        topOfStack++;
        data[topOfStack] = newValue;

        if(data.length == topOfStack + 1) {
            resize(data.length * 2);
        }
    }

    public AnyType top()
    {
        if(isEmpty()) {
            System.out.println("Empty stack.");
            System.exit(0);
        }

        return data[topOfStack];
    }

    public AnyType pop()
    {
        AnyType thing = data[topOfStack];

        data[topOfStack] = null;

        if(isEmpty()) {
            System.out.println("Empty stack.");
            System.exit(0);
        }

        topOfStack--;

        if((topOfStack + 1) / 4 == data.length / 4) {
            resize(data.length / 2);
        }

        return thing;
    }

    @SuppressWarnings("unchecked")
    protected void resize(int newCapacity)
    {
        int n = size();

        AnyType[] temp = (AnyType[]) new Object[newCapacity];

        for(int i = 0; i < n; i++) {
            temp[i] = data[i];
        }

        data = temp;
    }
}
