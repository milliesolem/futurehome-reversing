package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableMergeWithMaybe<T> extends AbstractFlowableWithUpstream<T, T> {
   final MaybeSource<? extends T> other;

   public FlowableMergeWithMaybe(Flowable<T> var1, MaybeSource<? extends T> var2) {
      super(var1);
      this.other = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      FlowableMergeWithMaybe.MergeWithObserver var2 = new FlowableMergeWithMaybe.MergeWithObserver(var1);
      var1.onSubscribe(var2);
      this.source.subscribe(var2);
      this.other.subscribe(var2.otherObserver);
   }

   static final class MergeWithObserver<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
      static final int OTHER_STATE_CONSUMED_OR_EMPTY = 2;
      static final int OTHER_STATE_HAS_VALUE = 1;
      private static final long serialVersionUID = -4592979584110982903L;
      volatile boolean cancelled;
      int consumed;
      final Subscriber<? super T> downstream;
      long emitted;
      final AtomicThrowable error;
      final int limit;
      volatile boolean mainDone;
      final AtomicReference<Subscription> mainSubscription;
      final FlowableMergeWithMaybe.MergeWithObserver.OtherObserver<T> otherObserver;
      volatile int otherState;
      final int prefetch;
      volatile SimplePlainQueue<T> queue;
      final AtomicLong requested;
      T singleItem;

      MergeWithObserver(Subscriber<? super T> var1) {
         this.downstream = var1;
         this.mainSubscription = new AtomicReference<>();
         this.otherObserver = new FlowableMergeWithMaybe.MergeWithObserver.OtherObserver<>(this);
         this.error = new AtomicThrowable();
         this.requested = new AtomicLong();
         int var2 = Flowable.bufferSize();
         this.prefetch = var2;
         this.limit = var2 - (var2 >> 2);
      }

      public void cancel() {
         this.cancelled = true;
         SubscriptionHelper.cancel(this.mainSubscription);
         DisposableHelper.dispose(this.otherObserver);
         if (this.getAndIncrement() == 0) {
            this.queue = null;
            this.singleItem = null;
         }
      }

      void drain() {
         if (this.getAndIncrement() == 0) {
            this.drainLoop();
         }
      }

      void drainLoop() {
         Subscriber var13 = this.downstream;
         long var8 = this.emitted;
         int var1 = this.consumed;
         int var4 = this.limit;
         int var2 = 1;

         while (true) {
            long var10 = this.requested.get();

            while (true) {
               long var20;
               int var5 = (var20 = var8 - var10) == 0L ? 0 : (var20 < 0L ? -1 : 1);
               if (var5) {
                  if (this.cancelled) {
                     this.singleItem = null;
                     this.queue = null;
                     return;
                  }

                  if (this.error.get() != null) {
                     this.singleItem = null;
                     this.queue = null;
                     var13.onError(this.error.terminate());
                     return;
                  }

                  int var6 = this.otherState;
                  if (var6 == 1) {
                     Object var19 = this.singleItem;
                     this.singleItem = null;
                     this.otherState = 2;
                     var13.onNext(var19);
                     var8++;
                     continue;
                  }

                  boolean var7 = this.mainDone;
                  SimplePlainQueue var12 = this.queue;
                  Object var17;
                  if (var12 != null) {
                     var17 = var12.poll();
                  } else {
                     var17 = null;
                  }

                  boolean var3;
                  if (var17 == null) {
                     var3 = 1;
                  } else {
                     var3 = 0;
                  }

                  if (var7 && var3 && var6 == 2) {
                     this.queue = null;
                     var13.onComplete();
                     return;
                  }

                  if (!var3) {
                     var13.onNext(var17);
                     var8++;
                     var3 = var1 + 1;
                     var1 = var3;
                     if (var3 == var4) {
                        this.mainSubscription.get().request(var4);
                        var1 = 0;
                     }
                     continue;
                  }
               }

               if (!var5) {
                  if (this.cancelled) {
                     this.singleItem = null;
                     this.queue = null;
                     return;
                  }

                  if (this.error.get() != null) {
                     this.singleItem = null;
                     this.queue = null;
                     var13.onError(this.error.terminate());
                     return;
                  }

                  boolean var16 = this.mainDone;
                  SimplePlainQueue var18 = this.queue;
                  boolean var14;
                  if (var18 != null && !var18.isEmpty()) {
                     var14 = false;
                  } else {
                     var14 = true;
                  }

                  if (var16 && var14 && this.otherState == 2) {
                     this.queue = null;
                     var13.onComplete();
                     return;
                  }
               }

               this.emitted = var8;
               this.consumed = var1;
               var2 = this.addAndGet(-var2);
               if (var2 == 0) {
                  return;
               }
               break;
            }
         }
      }

      SimplePlainQueue<T> getOrCreateQueue() {
         SimplePlainQueue var2 = this.queue;
         Object var1 = var2;
         if (var2 == null) {
            var1 = new SpscArrayQueue(Flowable.bufferSize());
            this.queue = (SimplePlainQueue<T>)var1;
         }

         return (SimplePlainQueue<T>)var1;
      }

      public void onComplete() {
         this.mainDone = true;
         this.drain();
      }

      public void onError(Throwable var1) {
         if (this.error.addThrowable(var1)) {
            DisposableHelper.dispose(this.otherObserver);
            this.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      public void onNext(T var1) {
         if (this.compareAndSet(0, 1)) {
            long var3 = this.emitted;
            if (this.requested.get() != var3) {
               SimplePlainQueue var5 = this.queue;
               if (var5 != null && !var5.isEmpty()) {
                  var5.offer(var1);
               } else {
                  this.emitted = var3 + 1L;
                  this.downstream.onNext(var1);
                  int var2 = this.consumed + 1;
                  if (var2 == this.limit) {
                     this.consumed = 0;
                     this.mainSubscription.get().request(var2);
                  } else {
                     this.consumed = var2;
                  }
               }
            } else {
               this.getOrCreateQueue().offer((T)var1);
            }

            if (this.decrementAndGet() == 0) {
               return;
            }
         } else {
            this.getOrCreateQueue().offer((T)var1);
            if (this.getAndIncrement() != 0) {
               return;
            }
         }

         this.drainLoop();
      }

      @Override
      public void onSubscribe(Subscription var1) {
         SubscriptionHelper.setOnce(this.mainSubscription, var1, this.prefetch);
      }

      void otherComplete() {
         this.otherState = 2;
         this.drain();
      }

      void otherError(Throwable var1) {
         if (this.error.addThrowable(var1)) {
            SubscriptionHelper.cancel(this.mainSubscription);
            this.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      void otherSuccess(T var1) {
         if (this.compareAndSet(0, 1)) {
            long var2 = this.emitted;
            if (this.requested.get() != var2) {
               this.emitted = var2 + 1L;
               this.downstream.onNext(var1);
               this.otherState = 2;
            } else {
               this.singleItem = (T)var1;
               this.otherState = 1;
               if (this.decrementAndGet() == 0) {
                  return;
               }
            }
         } else {
            this.singleItem = (T)var1;
            this.otherState = 1;
            if (this.getAndIncrement() != 0) {
               return;
            }
         }

         this.drainLoop();
      }

      public void request(long var1) {
         BackpressureHelper.add(this.requested, var1);
         this.drain();
      }

      static final class OtherObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T> {
         private static final long serialVersionUID = -2935427570954647017L;
         final FlowableMergeWithMaybe.MergeWithObserver<T> parent;

         OtherObserver(FlowableMergeWithMaybe.MergeWithObserver<T> var1) {
            this.parent = var1;
         }

         @Override
         public void onComplete() {
            this.parent.otherComplete();
         }

         @Override
         public void onError(Throwable var1) {
            this.parent.otherError(var1);
         }

         @Override
         public void onSubscribe(Disposable var1) {
            DisposableHelper.setOnce(this, var1);
         }

         @Override
         public void onSuccess(T var1) {
            this.parent.otherSuccess((T)var1);
         }
      }
   }
}
