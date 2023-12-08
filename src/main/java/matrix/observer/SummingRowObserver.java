package matrix.observer;

import matrix.CommonMatrix;
import matrix.DrawableMatrix;
import matrix.util.MatrixInitializer;

import java.util.Objects;

public class SummingRowObserver<T extends Number> implements PassiveObserver<T> {

    private Long idState;
    private PassiveObservable<T> subject;
    private DrawableMatrix<T> state;

    public SummingRowObserver(PassiveObservable<T> subject) {
        this.idState = null;
        this.subject = subject;
        this.state = new CommonMatrix<>(subject.getState().getRowCount(), 1);
        MatrixInitializer.initialize(state, 0, (T) Double.valueOf(0));
    }

    public DrawableMatrix<T> getState() {
        if (!Objects.equals(idState, subject.getIdState())) {
            System.out.printf(
                    "idState=%d, subject.state=%d <- update%n", idState, subject.getIdState()
            );
            update();
        }

        return state;
    }

    @Override
    public void attach(PassiveObservable<T> subject) {
        this.subject = subject;
    }

    @Override
    public void detach(PassiveObservable<T> subject) {
        this.subject = null;
    }

    @Override
    public void update() {
        updateMatrixDimension();
        updateState(subject.getState());
        this.idState = subject.getIdState();
    }

    private void updateState(DrawableMatrix<T> other) {
        for (int i = 0; i < other.getRowCount(); i++) {
            double rowSum = 0;
            for (int j = 0; j < other.getColumnCount(); j++) {
                rowSum += other.get(i, j).doubleValue();
            }
            state.set(i, 0, (T) Double.valueOf(rowSum));
        }
    }

    private void updateMatrixDimension() {
        int subjectRowCount = subject.getState().getRowCount();
        if (subjectRowCount != state.getRowCount()) {
            state = new CommonMatrix<>(subjectRowCount, 1);
            MatrixInitializer.initialize(state, 0, (T) Double.valueOf(0));
        }
    }
}
