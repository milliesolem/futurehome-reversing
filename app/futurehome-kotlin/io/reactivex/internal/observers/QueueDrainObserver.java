package io.reactivex.internal.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.util.ObservableQueueDrain;
import io.reactivex.internal.util.QueueDrainHelper;

public abstract class QueueDrainObserver<T, U, V> extends QueueDrainSubscriberPad2 implements Observer<T>, ObservableQueueDrain<U, V> {
   protected volatile boolean cancelled;
   protected volatile boolean done;
   protected final Observer<? super V> downstream;
   protected Throwable error;
   protected final SimplePlainQueue<U> queue;

   public QueueDrainObserver(Observer<? super V> var1, SimplePlainQueue<U> var2) {
      this.downstream = var1;
      this.queue = var2;
   }

   @Override
   public void accept(Observer<? super V> var1, U var2) {
   }

   @Override
   public final boolean cancelled() {
      return this.cancelled;
   }

   @Override
   public final boolean done() {
      return this.done;
   }

   @Override
   public final boolean enter() {
      boolean var1;
      if (this.wip.getAndIncrement() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public final Throwable error() {
      return this.error;
   }

   public final boolean fastEnter() {
      int var1 = this.wip.get();
      boolean var3 = false;
      boolean var2 = var3;
      if (var1 == 0) {
         var2 = var3;
         if (this.wip.compareAndSet(0, 1)) {
            var2 = true;
         }
      }

      return var2;
   }

   protected final void fastPathEmit(U var1, boolean var2, Disposable var3) {
      Observer var4 = this.downstream;
      SimplePlainQueue var5 = this.queue;
      if (this.wip.get() == 0 && this.wip.compareAndSet(0, 1)) {
         this.accept(var4, (U)var1);
         if (this.leave(-1) == 0) {
            return;
         }
      } else {
         var5.offer(var1);
         if (!this.enter()) {
            return;
         }
      }

      QueueDrainHelper.drainLoop(var5, var4, var2, var3, this);
   }

   protected final void fastPathOrderedEmit(U var1, boolean var2, Disposable var3) {
      Observer var5 = this.downstream;
      SimplePlainQueue var4 = this.queue;
      if (this.wip.get() != 0 || !this.wip.compareAndSet(0, 1)) {
         var4.offer(var1);
         if (!this.enter()) {
            return;
         }
      } else if (var4.isEmpty()) {
         this.accept(var5, (U)var1);
         if (this.leave(-1) == 0) {
            return;
         }
      } else {
         var4.offer(var1);
      }

      QueueDrainHelper.drainLoop(var4, var5, var2, var3, this);
   }

   @Override
   public final int leave(int var1) {
      return this.wip.addAndGet(var1);
   }
}
