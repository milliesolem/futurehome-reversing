package io.reactivex.internal.subscriptions;

import io.reactivex.internal.fuseable.QueueSubscription;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class BasicIntQueueSubscription<T> extends AtomicInteger implements QueueSubscription<T> {
   private static final long serialVersionUID = -6671519529404341862L;

   @Override
   public final boolean offer(T var1) {
      throw new UnsupportedOperationException("Should not be called!");
   }

   @Override
   public final boolean offer(T var1, T var2) {
      throw new UnsupportedOperationException("Should not be called!");
   }
}
