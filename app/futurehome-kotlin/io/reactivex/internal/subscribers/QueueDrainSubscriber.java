package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.QueueDrain;
import io.reactivex.internal.util.QueueDrainHelper;
import org.reactivestreams.Subscriber;

public abstract class QueueDrainSubscriber<T, U, V> extends QueueDrainSubscriberPad4 implements FlowableSubscriber<T>, QueueDrain<U, V> {
   protected volatile boolean cancelled;
   protected volatile boolean done;
   protected final Subscriber<? super V> downstream;
   protected Throwable error;
   protected final SimplePlainQueue<U> queue;

   public QueueDrainSubscriber(Subscriber<? super V> var1, SimplePlainQueue<U> var2) {
      this.downstream = var1;
      this.queue = var2;
   }

   @Override
   public boolean accept(Subscriber<? super V> var1, U var2) {
      return false;
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

   protected final void fastPathEmitMax(U var1, boolean var2, Disposable var3) {
      Subscriber var7 = this.downstream;
      SimplePlainQueue var6 = this.queue;
      if (this.fastEnter()) {
         long var4 = this.requested.get();
         if (var4 == 0L) {
            var3.dispose();
            var7.onError(new MissingBackpressureException("Could not emit buffer due to lack of requests"));
            return;
         }

         if (this.accept(var7, (U)var1) && var4 != Long.MAX_VALUE) {
            this.produced(1L);
         }

         if (this.leave(-1) == 0) {
            return;
         }
      } else {
         var6.offer(var1);
         if (!this.enter()) {
            return;
         }
      }

      QueueDrainHelper.drainMaxLoop(var6, var7, var2, var3, this);
   }

   protected final void fastPathOrderedEmitMax(U var1, boolean var2, Disposable var3) {
      Subscriber var6 = this.downstream;
      SimplePlainQueue var7 = this.queue;
      if (this.fastEnter()) {
         long var4 = this.requested.get();
         if (var4 == 0L) {
            this.cancelled = true;
            var3.dispose();
            var6.onError(new MissingBackpressureException("Could not emit buffer due to lack of requests"));
            return;
         }

         if (var7.isEmpty()) {
            if (this.accept(var6, (U)var1) && var4 != Long.MAX_VALUE) {
               this.produced(1L);
            }

            if (this.leave(-1) == 0) {
               return;
            }
         } else {
            var7.offer(var1);
         }
      } else {
         var7.offer(var1);
         if (!this.enter()) {
            return;
         }
      }

      QueueDrainHelper.drainMaxLoop(var7, var6, var2, var3, this);
   }

   @Override
   public final int leave(int var1) {
      return this.wip.addAndGet(var1);
   }

   @Override
   public final long produced(long var1) {
      return this.requested.addAndGet(-var1);
   }

   @Override
   public final long requested() {
      return this.requested.get();
   }

   public final void requested(long var1) {
      if (SubscriptionHelper.validate(var1)) {
         BackpressureHelper.add(this.requested, var1);
      }
   }
}
