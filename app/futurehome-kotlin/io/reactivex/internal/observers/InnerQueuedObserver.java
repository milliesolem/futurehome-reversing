package io.reactivex.internal.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.util.QueueDrainHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class InnerQueuedObserver<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable {
   private static final long serialVersionUID = -5417183359794346637L;
   volatile boolean done;
   int fusionMode;
   final InnerQueuedObserverSupport<T> parent;
   final int prefetch;
   SimpleQueue<T> queue;

   public InnerQueuedObserver(InnerQueuedObserverSupport<T> var1, int var2) {
      this.parent = var1;
      this.prefetch = var2;
   }

   @Override
   public void dispose() {
      DisposableHelper.dispose(this);
   }

   public int fusionMode() {
      return this.fusionMode;
   }

   @Override
   public boolean isDisposed() {
      return DisposableHelper.isDisposed(this.get());
   }

   public boolean isDone() {
      return this.done;
   }

   @Override
   public void onComplete() {
      this.parent.innerComplete(this);
   }

   @Override
   public void onError(Throwable var1) {
      this.parent.innerError(this, var1);
   }

   @Override
   public void onNext(T var1) {
      if (this.fusionMode == 0) {
         this.parent.innerNext(this, (T)var1);
      } else {
         this.parent.drain();
      }
   }

   @Override
   public void onSubscribe(Disposable var1) {
      if (DisposableHelper.setOnce(this, var1)) {
         if (var1 instanceof QueueDisposable) {
            QueueDisposable var3 = (QueueDisposable)var1;
            int var2 = var3.requestFusion(3);
            if (var2 == 1) {
               this.fusionMode = var2;
               this.queue = var3;
               this.done = true;
               this.parent.innerComplete(this);
               return;
            }

            if (var2 == 2) {
               this.fusionMode = var2;
               this.queue = var3;
               return;
            }
         }

         this.queue = QueueDrainHelper.createQueue(-this.prefetch);
      }
   }

   public SimpleQueue<T> queue() {
      return this.queue;
   }

   public void setDone() {
      this.done = true;
   }
}
