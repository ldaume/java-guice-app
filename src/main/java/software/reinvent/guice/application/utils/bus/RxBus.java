package software.reinvent.guice.application.bus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Eventbus implementation with RxJava.
 *
 * @author leonard Daume
 */
public class RxBus {

    private final Subject<Object, Object> bus = new SerializedSubject<>(PublishSubject.create());

    public void send(Object event) {
        bus.onNext(event);
    }

    public Observable<Object> toObserverable() {
        return bus;
    }
}
