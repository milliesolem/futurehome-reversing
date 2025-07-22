package io.reactivex.internal.subscriptions;

import io.reactivex.internal.fuseable.QueueSubscription;
import java.util.concurrent.atomic.AtomicLong;

public abstract class BasicQueueSubscription<T> extends AtomicLong implements QueueSubscription<T> {
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
