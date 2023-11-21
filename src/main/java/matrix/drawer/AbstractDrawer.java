package matrix.drawer;

import matrix.DrawableMatrix;
import matrix.util.MatrixStatisticsProvider;

public abstract class AbstractDrawer<T extends Number> implements Drawer<T> {

    protected boolean bordersActive;

    public AbstractDrawer(boolean bordersActive) {
        this.bordersActive = bordersActive;
    }

    protected int getCellSize(DrawableMatrix<T> matrix) {
        MatrixStatisticsProvider<T> statistics = new MatrixStatisticsProvider<>(matrix);

        int maxValue = statistics.getMax().intValue();
        int minValue = statistics.getMin().intValue();

        double maxAbs = Math.max(
                Math.abs(maxValue),
                Math.abs(minValue)
        );

        int maxValueLength = String.valueOf(maxAbs).split(",")[0].length();
        return maxValueLength + 2 + (bordersActive ? 1 : 0);
    }
}
