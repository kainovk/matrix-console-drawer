package matrix.observer;

public interface PassiveObserver<T extends Number> {

    void attach(PassiveObservable<T> subject);

    void detach(PassiveObservable<T> subject);

    void update();
}
