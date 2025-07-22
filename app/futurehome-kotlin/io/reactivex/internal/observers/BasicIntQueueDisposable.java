package io.reactivex.internal.observers;

import io.reactivex.internal.fuseable.QueueDisposable;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class BasicIntQueueDisposable<T> extends AtomicInteger implements QueueDisposable<T> {
   private static final long serialVersionUID = -1001730202384742097L;

   @Override
   public final boolean offer(T var1) {
      throw new UnsupportedOperationException("Should not be called");
   }

   @Override
   public final boolean offer(T var1, T var2) {
      throw new UnsupportedOperationException("Should not be called");
   }
}
