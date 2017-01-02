package software.reinvent.guice.application.utils.bus;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Eventbus implementation with RxJava.
 *
 * @author leonard Daume
 */
public class RxBus {

    private final PublishSubject<Object> bus = PublishSubject.create();

    public void send(final Object event) {
        bus.onNext(event);
    }

    public Observable<Object> toObservable() {
        return bus;
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }
}
