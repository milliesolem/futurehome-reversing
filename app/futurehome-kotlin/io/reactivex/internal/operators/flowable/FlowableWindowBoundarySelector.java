package io.reactivex.internal.operators.flowable;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscribers.QueueDrainSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.processors.UnicastProcessor;
import io.reactivex.subscribers.DisposableSubscriber;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableWindowBoundarySelector<T, B, V> extends AbstractFlowableWithUpstream<T, Flowable<T>> {
   final int bufferSize;
   final Function<? super B, ? extends Publisher<V>> close;
   final Publisher<B> open;

   public FlowableWindowBoundarySelector(Flowable<T> var1, Publisher<B> var2, Function<? super B, ? extends Publisher<V>> var3, int var4) {
      super(var1);
      this.open = var2;
      this.close = var3;
      this.bufferSize = var4;
   }

   @Override
   protected void subscribeActual(Subscriber<? super Flowable<T>> var1) {
      this.source
         .subscribe(new FlowableWindowBoundarySelector.WindowBoundaryMainSubscriber<>(new SerializedSubscriber<>(var1), this.open, this.close, this.bufferSize));
   }

   static final class OperatorWindowBoundaryCloseSubscriber<T, V> extends DisposableSubscriber<V> {
      boolean done;
      final FlowableWindowBoundarySelector.WindowBoundaryMainSubscriber<T, ?, V> parent;
      final UnicastProcessor<T> w;

      OperatorWindowBoundaryCloseSubscriber(FlowableWindowBoundarySelector.WindowBoundaryMainSubscriber<T, ?, V> var1, UnicastProcessor<T> var2) {
         this.parent = var1;
         this.w = var2;
      }

      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.parent.close(this);
         }
      }

      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.parent.error(var1);
         }
      }

      public void onNext(V var1) {
         this.cancel();
         this.onComplete();
      }
   }

   static final class OperatorWindowBoundaryOpenSubscriber<T, B> extends DisposableSubscriber<B> {
      final FlowableWindowBoundarySelector.WindowBoundaryMainSubscriber<T, B, ?> parent;

      OperatorWindowBoundaryOpenSubscriber(FlowableWindowBoundarySelector.WindowBoundaryMainSubscriber<T, B, ?> var1) {
         this.parent = var1;
      }

      public void onComplete() {
         this.parent.onComplete();
      }

      public void onError(Throwable var1) {
         this.parent.error(var1);
      }

      public void onNext(B var1) {
         this.parent.open((B)var1);
      }
   }

   static final class WindowBoundaryMainSubscriber<T, B, V> extends QueueDrainSubscriber<T, Object, Flowable<T>> implements Subscription {
      final AtomicReference<Disposable> boundary = new AtomicReference<>();
      final int bufferSize;
      final Function<? super B, ? extends Publisher<V>> close;
      final Publisher<B> open;
      final CompositeDisposable resources;
      final AtomicBoolean stopWindows;
      Subscription upstream;
      final AtomicLong windows;
      final List<UnicastProcessor<T>> ws;

      WindowBoundaryMainSubscriber(Subscriber<? super Flowable<T>> var1, Publisher<B> var2, Function<? super B, ? extends Publisher<V>> var3, int var4) {
         super(var1, new MpscLinkedQueue<>());
         AtomicLong var5 = new AtomicLong();
         this.windows = var5;
         this.stopWindows = new AtomicBoolean();
         this.open = var2;
         this.close = var3;
         this.bufferSize = var4;
         this.resources = new CompositeDisposable();
         this.ws = new ArrayList<>();
         var5.lazySet(1L);
      }

      @Override
      public boolean accept(Subscriber<? super Flowable<T>> var1, Object var2) {
         return false;
      }

      public void cancel() {
         if (this.stopWindows.compareAndSet(false, true)) {
            DisposableHelper.dispose(this.boundary);
            if (this.windows.decrementAndGet() == 0L) {
               this.upstream.cancel();
            }
         }
      }

      void close(FlowableWindowBoundarySelector.OperatorWindowBoundaryCloseSubscriber<T, V> var1) {
         this.resources.delete(var1);
         this.queue.offer(new FlowableWindowBoundarySelector.WindowOperation<>(var1.w, null));
         if (this.enter()) {
            this.drainLoop();
         }
      }

      void dispose() {
         this.resources.dispose();
         DisposableHelper.dispose(this.boundary);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void drainLoop() {
         SimplePlainQueue var8 = this.queue;
         Subscriber var7 = this.downstream;
         List var6 = this.ws;
         int var1 = 1;

         while (true) {
            boolean var5 = this.done;
            Object var10 = var8.poll();
            boolean var2;
            if (var10 == null) {
               var2 = 1;
            } else {
               var2 = 0;
            }

            if (var5 && var2) {
               this.dispose();
               Throwable var16 = this.error;
               if (var16 != null) {
                  Iterator var14 = var6.iterator();

                  while (var14.hasNext()) {
                     ((UnicastProcessor)var14.next()).onError(var16);
                  }
               } else {
                  Iterator var15 = var6.iterator();

                  while (var15.hasNext()) {
                     ((UnicastProcessor)var15.next()).onComplete();
                  }
               }

               var6.clear();
               return;
            }

            if (var2) {
               var2 = this.leave(-var1);
               var1 = var2;
               if (var2 == 0) {
                  return;
               }
            } else if (var10 instanceof FlowableWindowBoundarySelector.WindowOperation) {
               FlowableWindowBoundarySelector.WindowOperation var17 = (FlowableWindowBoundarySelector.WindowOperation)var10;
               if (var17.w != null) {
                  if (var6.remove(var17.w)) {
                     var17.w.onComplete();
                     if (this.windows.decrementAndGet() == 0L) {
                        this.dispose();
                        return;
                     }
                  }
               } else if (!this.stopWindows.get()) {
                  var10 = UnicastProcessor.create(this.bufferSize);
                  long var3 = this.requested();
                  if (var3 != 0L) {
                     var6.add(var10);
                     var7.onNext(var10);
                     if (var3 != Long.MAX_VALUE) {
                        this.produced(1L);
                     }

                     try {
                        var18 = ObjectHelper.requireNonNull(this.close.apply(var17.open), "The publisher supplied is null");
                     } catch (Throwable var12) {
                        this.cancel();
                        var7.onError(var12);
                        continue;
                     }

                     var10 = new FlowableWindowBoundarySelector.OperatorWindowBoundaryCloseSubscriber<>(this, (UnicastProcessor<T>)var10);
                     if (this.resources.add((Disposable)var10)) {
                        this.windows.getAndIncrement();
                        var18.subscribe((Subscriber)var10);
                     }
                  } else {
                     this.cancel();
                     var7.onError(new MissingBackpressureException("Could not deliver new window due to lack of requests"));
                  }
               }
            } else {
               Iterator var9 = var6.iterator();

               while (var9.hasNext()) {
                  ((UnicastProcessor)var9.next()).onNext(NotificationLite.getValue(var10));
               }
            }
         }
      }

      void error(Throwable var1) {
         this.upstream.cancel();
         this.resources.dispose();
         DisposableHelper.dispose(this.boundary);
         this.downstream.onError(var1);
      }

      public void onComplete() {
         if (!this.done) {
            this.done = true;
            if (this.enter()) {
               this.drainLoop();
            }

            if (this.windows.decrementAndGet() == 0L) {
               this.resources.dispose();
            }

            this.downstream.onComplete();
         }
      }

      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.error = var1;
            this.done = true;
            if (this.enter()) {
               this.drainLoop();
            }

            if (this.windows.decrementAndGet() == 0L) {
               this.resources.dispose();
            }

            this.downstream.onError(var1);
         }
      }

      public void onNext(T var1) {
         if (!this.done) {
            if (this.fastEnter()) {
               Iterator var2 = this.ws.iterator();

               while (var2.hasNext()) {
                  ((UnicastProcessor)var2.next()).onNext(var1);
               }

               if (this.leave(-1) == 0) {
                  return;
               }
            } else {
               this.queue.offer(NotificationLite.next(var1));
               if (!this.enter()) {
                  return;
               }
            }

            this.drainLoop();
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            if (this.stopWindows.get()) {
               return;
            }

            FlowableWindowBoundarySelector.OperatorWindowBoundaryOpenSubscriber var2 = new FlowableWindowBoundarySelector.OperatorWindowBoundaryOpenSubscriber<>(
               this
            );
            if (ExternalSyntheticBackportWithForwarding0.m(this.boundary, null, var2)) {
               var1.request(Long.MAX_VALUE);
               this.open.subscribe(var2);
            }
         }
      }

      void open(B var1) {
         this.queue.offer(new FlowableWindowBoundarySelector.WindowOperation<>(null, var1));
         if (this.enter()) {
            this.drainLoop();
         }
      }

      public void request(long var1) {
         this.requested(var1);
      }
   }

   static final class WindowOperation<T, B> {
      final B open;
      final UnicastProcessor<T> w;

      WindowOperation(UnicastProcessor<T> var1, B var2) {
         this.w = var1;
         this.open = (B)var2;
      }
   }
}
