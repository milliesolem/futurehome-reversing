package io.reactivex.internal.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.NotificationLite;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

public final class BlockingObserver<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable {
   public static final Object TERMINATED = new Object();
   private static final long serialVersionUID = -4875965440900746268L;
   final Queue<Object> queue;

   public BlockingObserver(Queue<Object> var1) {
      this.queue = var1;
   }

   @Override
   public void dispose() {
      if (DisposableHelper.dispose(this)) {
         this.queue.offer(TERMINATED);
      }
   }

   @Override
   public boolean isDisposed() {
      boolean var1;
      if (this.get() == DisposableHelper.DISPOSED) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public void onComplete() {
      this.queue.offer(NotificationLite.complete());
   }

   @Override
   public void onError(Throwable var1) {
      this.queue.offer(NotificationLite.error(var1));
   }

   @Override
   public void onNext(T var1) {
      this.queue.offer(NotificationLite.next(var1));
   }

   @Override
   public void onSubscribe(Disposable var1) {
      DisposableHelper.setOnce(this, var1);
   }
}
