package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.processors.UnicastProcessor;
import io.reactivex.subscribers.DisposableSubscriber;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableWindowBoundary<T, B> extends AbstractFlowableWithUpstream<T, Flowable<T>> {
   final int capacityHint;
   final Publisher<B> other;

   public FlowableWindowBoundary(Flowable<T> var1, Publisher<B> var2, int var3) {
      super(var1);
      this.other = var2;
      this.capacityHint = var3;
   }

   @Override
   protected void subscribeActual(Subscriber<? super Flowable<T>> var1) {
      FlowableWindowBoundary.WindowBoundaryMainSubscriber var2 = new FlowableWindowBoundary.WindowBoundaryMainSubscriber(var1, this.capacityHint);
      var1.onSubscribe(var2);
      var2.innerNext();
      this.other.subscribe(var2.boundarySubscriber);
      this.source.subscribe(var2);
   }

   static final class WindowBoundaryInnerSubscriber<T, B> extends DisposableSubscriber<B> {
      boolean done;
      final FlowableWindowBoundary.WindowBoundaryMainSubscriber<T, B> parent;

      WindowBoundaryInnerSubscriber(FlowableWindowBoundary.WindowBoundaryMainSubscriber<T, B> var1) {
         this.parent = var1;
      }

      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.parent.innerComplete();
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

      public void onNext(B var1) {
         if (!this.done) {
            this.parent.innerNext();
         }
      }
   }

   static final class WindowBoundaryMainSubscriber<T, B> extends AtomicInteger implements FlowableSubscriber<T>, Subscription, Runnable {
      static final Object NEXT_WINDOW = new Object();
      private static final long serialVersionUID = 2233020065421370272L;
      final FlowableWindowBoundary.WindowBoundaryInnerSubscriber<T, B> boundarySubscriber;
      final int capacityHint;
      volatile boolean done;
      final Subscriber<? super Flowable<T>> downstream;
      long emitted;
      final AtomicThrowable errors;
      final MpscLinkedQueue<Object> queue;
      final AtomicLong requested;
      final AtomicBoolean stopWindows;
      final AtomicReference<Subscription> upstream;
      UnicastProcessor<T> window;
      final AtomicInteger windows;

      WindowBoundaryMainSubscriber(Subscriber<? super Flowable<T>> var1, int var2) {
         this.downstream = var1;
         this.capacityHint = var2;
         this.boundarySubscriber = new FlowableWindowBoundary.WindowBoundaryInnerSubscriber<>(this);
         this.upstream = new AtomicReference<>();
         this.windows = new AtomicInteger(1);
         this.queue = new MpscLinkedQueue<>();
         this.errors = new AtomicThrowable();
         this.stopWindows = new AtomicBoolean();
         this.requested = new AtomicLong();
      }

      public void cancel() {
         if (this.stopWindows.compareAndSet(false, true)) {
            this.boundarySubscriber.dispose();
            if (this.windows.decrementAndGet() == 0) {
               SubscriptionHelper.cancel(this.upstream);
            }
         }
      }

      void drain() {
         if (this.getAndIncrement() == 0) {
            Subscriber var6 = this.downstream;
            MpscLinkedQueue var9 = this.queue;
            AtomicThrowable var8 = this.errors;
            long var4 = this.emitted;
            int var1 = 1;

            while (this.windows.get() != 0) {
               UnicastProcessor var7 = this.window;
               boolean var3 = this.done;
               if (var3 && var8.get() != null) {
                  var9.clear();
                  Throwable var14 = var8.terminate();
                  if (var7 != null) {
                     this.window = null;
                     var7.onError(var14);
                  }

                  var6.onError(var14);
                  return;
               }

               Object var10 = var9.poll();
               boolean var2;
               if (var10 == null) {
                  var2 = 1;
               } else {
                  var2 = 0;
               }

               if (var3 && var2) {
                  Throwable var13 = var8.terminate();
                  if (var13 == null) {
                     if (var7 != null) {
                        this.window = null;
                        var7.onComplete();
                     }

                     var6.onComplete();
                  } else {
                     if (var7 != null) {
                        this.window = null;
                        var7.onError(var13);
                     }

                     var6.onError(var13);
                  }

                  return;
               }

               if (var2) {
                  this.emitted = var4;
                  var2 = this.addAndGet(-var1);
                  var1 = var2;
                  if (var2 == 0) {
                     return;
                  }
               } else if (var10 != NEXT_WINDOW) {
                  var7.onNext(var10);
               } else {
                  if (var7 != null) {
                     this.window = null;
                     var7.onComplete();
                  }

                  if (!this.stopWindows.get()) {
                     var7 = UnicastProcessor.create(this.capacityHint, this);
                     this.window = var7;
                     this.windows.getAndIncrement();
                     if (var4 != this.requested.get()) {
                        var4++;
                        var6.onNext(var7);
                     } else {
                        SubscriptionHelper.cancel(this.upstream);
                        this.boundarySubscriber.dispose();
                        var8.addThrowable(new MissingBackpressureException("Could not deliver a window due to lack of requests"));
                        this.done = true;
                     }
                  }
               }
            }

            var9.clear();
            this.window = null;
         }
      }

      void innerComplete() {
         SubscriptionHelper.cancel(this.upstream);
         this.done = true;
         this.drain();
      }

      void innerError(Throwable var1) {
         SubscriptionHelper.cancel(this.upstream);
         if (this.errors.addThrowable(var1)) {
            this.done = true;
            this.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      void innerNext() {
         this.queue.offer(NEXT_WINDOW);
         this.drain();
      }

      public void onComplete() {
         this.boundarySubscriber.dispose();
         this.done = true;
         this.drain();
      }

      public void onError(Throwable var1) {
         this.boundarySubscriber.dispose();
         if (this.errors.addThrowable(var1)) {
            this.done = true;
            this.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      public void onNext(T var1) {
         this.queue.offer(var1);
         this.drain();
      }

      @Override
      public void onSubscribe(Subscription var1) {
         SubscriptionHelper.setOnce(this.upstream, var1, Long.MAX_VALUE);
      }

      public void request(long var1) {
         BackpressureHelper.add(this.requested, var1);
      }

      @Override
      public void run() {
         if (this.windows.decrementAndGet() == 0) {
            SubscriptionHelper.cancel(this.upstream);
         }
      }
   }
}
