package io.reactivex.internal.fuseable;

public interface SimplePlainQueue<T> extends SimpleQueue<T> {
   @Override
   T poll();
}
