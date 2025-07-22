package io.reactivex.internal.observers;

import io.reactivex.internal.fuseable.QueueDisposable;

public abstract class BasicQueueDisposable<T> implements QueueDisposable<T> {
   @Override
   public final boolean offer(T var1) {
      throw new UnsupportedOperationException("Should not be called");
   }

   @Override
   public final boolean offer(T var1, T var2) {
      throw new UnsupportedOperationException("Should not be called");
   }
}
