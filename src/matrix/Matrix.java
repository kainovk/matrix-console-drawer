package matrix;

public interface Matrix<T extends Number> {

    T get(int row, int col);

    void set(int row, int col, T value);

    int getRowCount();

    int getColumnCount();
}
