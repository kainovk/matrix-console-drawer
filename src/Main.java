import matrix.CommonMatrix;
import matrix.util.MatrixInitializer;
import matrix.util.MatrixPrinter;
import matrix.util.MatrixStatisticsProvider;
import matrix.SparseMatrix;

public class Main {
    public static void main(String[] args) {
        CommonMatrix<Double> commonMatrix = new CommonMatrix<>(3, 3);
        SparseMatrix<Double> sparseMatrix = new SparseMatrix<>(3, 5);

        MatrixInitializer.initialize(commonMatrix, 5, 10.0);
        MatrixInitializer.initialize(sparseMatrix, 2, 10.0);

        System.out.println("Common Matrix: ");
        MatrixPrinter.print(commonMatrix);

        System.out.println("\nSparse Matrix: ");
        MatrixPrinter.print(sparseMatrix);

        MatrixStatisticsProvider<Double> ordinaryMatrixStats = new MatrixStatisticsProvider<>(commonMatrix);
        MatrixStatisticsProvider<Double> sparseMatrixStats = new MatrixStatisticsProvider<>(sparseMatrix);

        System.out.println("\nOrdinary Matrix Stats:");
        System.out.println("Sum: " + ordinaryMatrixStats.getSum());
        System.out.println("Average: " + ordinaryMatrixStats.getAverage());
        System.out.println("Max: " + ordinaryMatrixStats.getMax());
        System.out.println("Non-Zero Count: " + ordinaryMatrixStats.getNonZeroCount());

        System.out.println("\nSparse Matrix Stats:");
        System.out.println("Sum: " + sparseMatrixStats.getSum());
        System.out.println("Average: " + sparseMatrixStats.getAverage());
        System.out.println("Max: " + sparseMatrixStats.getMax());
        System.out.println("Non-Zero Count: " + sparseMatrixStats.getNonZeroCount());
    }
}