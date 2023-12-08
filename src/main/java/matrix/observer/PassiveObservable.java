package matrix.observer;

import matrix.DrawableMatrix;

public interface PassiveObservable<T extends Number> {

    Long getIdState();

    DrawableMatrix<T> getState();
}
