package io.reactivex.internal.operators.flowable;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.processors.UnicastProcessor;
import io.reactivex.subscribers.DisposableSubscriber;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableWindowBoundarySupplier<T, B> extends AbstractFlowableWithUpstream<T, Flowable<T>> {
   final int capacityHint;
   final Callable<? extends Publisher<B>> other;

   public FlowableWindowBoundarySupplier(Flowable<T> var1, Callable<? extends Publisher<B>> var2, int var3) {
      super(var1);
      this.other = var2;
      this.capacityHint = var3;
   }

   @Override
   protected void subscribeActual(Subscriber<? super Flowable<T>> var1) {
      FlowableWindowBoundarySupplier.WindowBoundaryMainSubscriber var2 = new FlowableWindowBoundarySupplier.WindowBoundaryMainSubscriber<>(
         var1, this.capacityHint, this.other
      );
      this.source.subscribe(var2);
   }

   static final class WindowBoundaryInnerSubscriber<T, B> extends DisposableSubscriber<B> {
      boolean done;
      final FlowableWindowBoundarySupplier.WindowBoundaryMainSubscriber<T, B> parent;

      WindowBoundaryInnerSubscriber(FlowableWindowBoundarySupplier.WindowBoundaryMainSubscriber<T, B> var1) {
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
            this.done = true;
            this.dispose();
            this.parent.innerNext(this);
         }
      }
   }

   static final class WindowBoundaryMainSubscriber<T, B> extends AtomicInteger implements FlowableSubscriber<T>, Subscription, Runnable {
      static final FlowableWindowBoundarySupplier.WindowBoundaryInnerSubscriber<Object, Object> BOUNDARY_DISPOSED = new FlowableWindowBoundarySupplier.WindowBoundaryInnerSubscriber<>(
         null
      );
      static final Object NEXT_WINDOW = new Object();
      private static final long serialVersionUID = 2233020065421370272L;
      final AtomicReference<FlowableWindowBoundarySupplier.WindowBoundaryInnerSubscriber<T, B>> boundarySubscriber;
      final int capacityHint;
      volatile boolean done;
      final Subscriber<? super Flowable<T>> downstream;
      long emitted;
      final AtomicThrowable errors;
      final Callable<? extends Publisher<B>> other;
      final MpscLinkedQueue<Object> queue;
      final AtomicLong requested;
      final AtomicBoolean stopWindows;
      Subscription upstream;
      UnicastProcessor<T> window;
      final AtomicInteger windows;

      WindowBoundaryMainSubscriber(Subscriber<? super Flowable<T>> var1, int var2, Callable<? extends Publisher<B>> var3) {
         this.downstream = var1;
         this.capacityHint = var2;
         this.boundarySubscriber = new AtomicReference<>();
         this.windows = new AtomicInteger(1);
         this.queue = new MpscLinkedQueue<>();
         this.errors = new AtomicThrowable();
         this.stopWindows = new AtomicBoolean();
         this.other = var3;
         this.requested = new AtomicLong();
      }

      public void cancel() {
         if (this.stopWindows.compareAndSet(false, true)) {
            this.disposeBoundary();
            if (this.windows.decrementAndGet() == 0) {
               this.upstream.cancel();
            }
         }
      }

      void disposeBoundary() {
         AtomicReference var2 = this.boundarySubscriber;
         FlowableWindowBoundarySupplier.WindowBoundaryInnerSubscriber var1 = BOUNDARY_DISPOSED;
         Disposable var3 = var2.getAndSet(var1);
         if (var3 != null && var3 != var1) {
            var3.dispose();
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void drain() {
         if (this.getAndIncrement() == 0) {
            Subscriber var6 = this.downstream;
            MpscLinkedQueue var8 = this.queue;
            AtomicThrowable var7 = this.errors;
            long var3 = this.emitted;
            int var1 = 1;

            while (this.windows.get() != 0) {
               UnicastProcessor var9 = this.window;
               boolean var5 = this.done;
               if (var5 && var7.get() != null) {
                  var8.clear();
                  Throwable var16 = var7.terminate();
                  if (var9 != null) {
                     this.window = null;
                     var9.onError(var16);
                  }

                  var6.onError(var16);
                  return;
               }

               Object var10 = var8.poll();
               boolean var2;
               if (var10 == null) {
                  var2 = 1;
               } else {
                  var2 = 0;
               }

               if (var5 && var2) {
                  Throwable var15 = var7.terminate();
                  if (var15 == null) {
                     if (var9 != null) {
                        this.window = null;
                        var9.onComplete();
                     }

                     var6.onComplete();
                  } else {
                     if (var9 != null) {
                        this.window = null;
                        var9.onError(var15);
                     }

                     var6.onError(var15);
                  }

                  return;
               }

               if (var2) {
                  this.emitted = var3;
                  var2 = this.addAndGet(-var1);
                  var1 = var2;
                  if (var2 == 0) {
                     return;
                  }
               } else if (var10 != NEXT_WINDOW) {
                  var9.onNext(var10);
               } else {
                  if (var9 != null) {
                     this.window = null;
                     var9.onComplete();
                  }

                  if (!this.stopWindows.get()) {
                     if (var3 != this.requested.get()) {
                        var10 = UnicastProcessor.create(this.capacityHint, this);
                        this.window = (UnicastProcessor<T>)var10;
                        this.windows.getAndIncrement();

                        Publisher var11;
                        try {
                           var11 = ObjectHelper.requireNonNull(this.other.call(), "The other Callable returned a null Publisher");
                        } catch (Throwable var13) {
                           Exceptions.throwIfFatal(var13);
                           var7.addThrowable(var13);
                           this.done = true;
                           continue;
                        }

                        FlowableWindowBoundarySupplier.WindowBoundaryInnerSubscriber var17 = new FlowableWindowBoundarySupplier.WindowBoundaryInnerSubscriber<>(
                           this
                        );
                        if (ExternalSyntheticBackportWithForwarding0.m(this.boundarySubscriber, null, var17)) {
                           var11.subscribe(var17);
                           var3++;
                           var6.onNext(var10);
                        }
                     } else {
                        this.upstream.cancel();
                        this.disposeBoundary();
                        var7.addThrowable(new MissingBackpressureException("Could not deliver a window due to lack of requests"));
                        this.done = true;
                     }
                  }
               }
            }

            var8.clear();
            this.window = null;
         }
      }

      void innerComplete() {
         this.upstream.cancel();
         this.done = true;
         this.drain();
      }

      void innerError(Throwable var1) {
         this.upstream.cancel();
         if (this.errors.addThrowable(var1)) {
            this.done = true;
            this.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      void innerNext(FlowableWindowBoundarySupplier.WindowBoundaryInnerSubscriber<T, B> var1) {
         ExternalSyntheticBackportWithForwarding0.m(this.boundarySubscriber, var1, null);
         this.queue.offer(NEXT_WINDOW);
         this.drain();
      }

      public void onComplete() {
         this.disposeBoundary();
         this.done = true;
         this.drain();
      }

      public void onError(Throwable var1) {
         this.disposeBoundary();
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
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            this.queue.offer(NEXT_WINDOW);
            this.drain();
            var1.request(Long.MAX_VALUE);
         }
      }

      public void request(long var1) {
         BackpressureHelper.add(this.requested, var1);
      }

      @Override
      public void run() {
         if (this.windows.decrementAndGet() == 0) {
            this.upstream.cancel();
         }
      }
   }
}
