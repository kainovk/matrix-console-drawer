package vector;

public interface Vector<T extends Number> {

    T get(int index);

    void set(int index, T value);

    int getSize();
}
