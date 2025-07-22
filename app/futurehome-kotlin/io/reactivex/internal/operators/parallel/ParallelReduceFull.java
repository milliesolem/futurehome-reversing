package io.reactivex.internal.operators.parallel;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelReduceFull<T> extends Flowable<T> {
   final BiFunction<T, T, T> reducer;
   final ParallelFlowable<? extends T> source;

   public ParallelReduceFull(ParallelFlowable<? extends T> var1, BiFunction<T, T, T> var2) {
      this.source = var1;
      this.reducer = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      ParallelReduceFull.ParallelReduceFullMainSubscriber var2 = new ParallelReduceFull.ParallelReduceFullMainSubscriber<>(
         var1, this.source.parallelism(), this.reducer
      );
      var1.onSubscribe(var2);
      this.source.subscribe(var2.subscribers);
   }

   static final class ParallelReduceFullInnerSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T> {
      private static final long serialVersionUID = -7954444275102466525L;
      boolean done;
      final ParallelReduceFull.ParallelReduceFullMainSubscriber<T> parent;
      final BiFunction<T, T, T> reducer;
      T value;

      ParallelReduceFullInnerSubscriber(ParallelReduceFull.ParallelReduceFullMainSubscriber<T> var1, BiFunction<T, T, T> var2) {
         this.parent = var1;
         this.reducer = var2;
      }

      void cancel() {
         SubscriptionHelper.cancel(this);
      }

      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.parent.innerComplete(this.value);
         }
      }

      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.parent.innerError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         if (!this.done) {
            Object var2 = this.value;
            if (var2 == null) {
               this.value = (T)var1;
            } else {
               try {
                  var1 = ObjectHelper.requireNonNull(this.reducer.apply((T)var2, (T)var1), "The reducer returned a null value");
               } catch (Throwable var4) {
                  Exceptions.throwIfFatal(var4);
                  this.get().cancel();
                  this.onError(var4);
                  return;
               }

               this.value = (T)var1;
            }
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         SubscriptionHelper.setOnce(this, var1, Long.MAX_VALUE);
      }
   }

   static final class ParallelReduceFullMainSubscriber<T> extends DeferredScalarSubscription<T> {
      private static final long serialVersionUID = -5370107872170712765L;
      final AtomicReference<ParallelReduceFull.SlotPair<T>> current = new AtomicReference<>();
      final AtomicReference<Throwable> error;
      final BiFunction<T, T, T> reducer;
      final AtomicInteger remaining = new AtomicInteger();
      final ParallelReduceFull.ParallelReduceFullInnerSubscriber<T>[] subscribers;

      ParallelReduceFullMainSubscriber(Subscriber<? super T> var1, int var2, BiFunction<T, T, T> var3) {
         super(var1);
         this.error = new AtomicReference<>();
         ParallelReduceFull.ParallelReduceFullInnerSubscriber[] var5 = new ParallelReduceFull.ParallelReduceFullInnerSubscriber[var2];

         for (int var4 = 0; var4 < var2; var4++) {
            var5[var4] = new ParallelReduceFull.ParallelReduceFullInnerSubscriber<>(this, var3);
         }

         this.subscribers = var5;
         this.reducer = var3;
         this.remaining.lazySet(var2);
      }

      ParallelReduceFull.SlotPair<T> addValue(T var1) {
         while (true) {
            ParallelReduceFull.SlotPair var4 = this.current.get();
            ParallelReduceFull.SlotPair var3 = var4;
            if (var4 == null) {
               var4 = new ParallelReduceFull.SlotPair();
               var3 = var4;
               if (!ExternalSyntheticBackportWithForwarding0.m(this.current, null, var4)) {
                  continue;
               }
            }

            int var2 = var3.tryAcquireSlot();
            if (var2 >= 0) {
               if (var2 == 0) {
                  var3.first = (T)var1;
               } else {
                  var3.second = (T)var1;
               }

               if (var3.releaseSlot()) {
                  ExternalSyntheticBackportWithForwarding0.m(this.current, var3, null);
                  return var3;
               }

               return null;
            }

            ExternalSyntheticBackportWithForwarding0.m(this.current, var3, null);
         }
      }

      @Override
      public void cancel() {
         ParallelReduceFull.ParallelReduceFullInnerSubscriber[] var3 = this.subscribers;
         int var2 = var3.length;

         for (int var1 = 0; var1 < var2; var1++) {
            var3[var1].cancel();
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void innerComplete(T var1) {
         if (var1 != null) {
            while (true) {
               var1 = this.addValue((T)var1);
               if (var1 == null) {
                  break;
               }

               try {
                  var1 = ObjectHelper.requireNonNull(this.reducer.apply(var1.first, var1.second), "The reducer returned a null value");
               } catch (Throwable var3) {
                  Exceptions.throwIfFatal(var3);
                  this.innerError(var3);
                  return;
               }
            }
         }

         if (this.remaining.decrementAndGet() == 0) {
            var1 = this.current.get();
            this.current.lazySet(null);
            if (var1 != null) {
               this.complete(var1.first);
            } else {
               this.downstream.onComplete();
            }
         }
      }

      void innerError(Throwable var1) {
         if (ExternalSyntheticBackportWithForwarding0.m(this.error, null, var1)) {
            this.cancel();
            this.downstream.onError(var1);
         } else if (var1 != this.error.get()) {
            RxJavaPlugins.onError(var1);
         }
      }
   }

   static final class SlotPair<T> extends AtomicInteger {
      private static final long serialVersionUID = 473971317683868662L;
      T first;
      final AtomicInteger releaseIndex = new AtomicInteger();
      T second;

      boolean releaseSlot() {
         boolean var1;
         if (this.releaseIndex.incrementAndGet() == 2) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      int tryAcquireSlot() {
         int var1;
         do {
            var1 = this.get();
            if (var1 >= 2) {
               return -1;
            }
         } while (!this.compareAndSet(var1, var1 + 1));

         return var1;
      }
   }
}
