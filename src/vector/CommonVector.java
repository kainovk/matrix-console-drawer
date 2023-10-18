package vector;

public class CommonVector<T extends Number> implements Vector<T> {

    private final T[] components;

    public CommonVector(int size) {
        components = (T[]) new Number[size];
    }

    @Override
    public int getSize() {
        return components.length;
    }

    @Override
    public T get(int index) {
        return components[index];
    }

    @Override
    public void set(int index, T value) {
        components[index] = value;
    }
}
