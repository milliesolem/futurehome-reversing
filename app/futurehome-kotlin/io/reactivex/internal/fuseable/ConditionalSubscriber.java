package io.reactivex.internal.fuseable;

import io.reactivex.FlowableSubscriber;

public interface ConditionalSubscriber<T> extends FlowableSubscriber<T> {
   boolean tryOnNext(T var1);
}
