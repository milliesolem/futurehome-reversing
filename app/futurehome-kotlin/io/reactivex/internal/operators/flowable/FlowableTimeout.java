package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableTimeout<T, U, V> extends AbstractFlowableWithUpstream<T, T> {
   final Publisher<U> firstTimeoutIndicator;
   final Function<? super T, ? extends Publisher<V>> itemTimeoutIndicator;
   final Publisher<? extends T> other;

   public FlowableTimeout(Flowable<T> var1, Publisher<U> var2, Function<? super T, ? extends Publisher<V>> var3, Publisher<? extends T> var4) {
      super(var1);
      this.firstTimeoutIndicator = var2;
      this.itemTimeoutIndicator = var3;
      this.other = var4;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      if (this.other == null) {
         FlowableTimeout.TimeoutSubscriber var2 = new FlowableTimeout.TimeoutSubscriber<>(var1, this.itemTimeoutIndicator);
         var1.onSubscribe(var2);
         var2.startFirstTimeout(this.firstTimeoutIndicator);
         this.source.subscribe(var2);
      } else {
         FlowableTimeout.TimeoutFallbackSubscriber var3 = new FlowableTimeout.TimeoutFallbackSubscriber<>(var1, this.itemTimeoutIndicator, this.other);
         var1.onSubscribe(var3);
         var3.startFirstTimeout(this.firstTimeoutIndicator);
         this.source.subscribe(var3);
      }
   }

   static final class TimeoutConsumer extends AtomicReference<Subscription> implements FlowableSubscriber<Object>, Disposable {
      private static final long serialVersionUID = 8708641127342403073L;
      final long idx;
      final FlowableTimeout.TimeoutSelectorSupport parent;

      TimeoutConsumer(long var1, FlowableTimeout.TimeoutSelectorSupport var3) {
         this.idx = var1;
         this.parent = var3;
      }

      @Override
      public void dispose() {
         SubscriptionHelper.cancel(this);
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.get() == SubscriptionHelper.CANCELLED) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public void onComplete() {
         if (this.get() != SubscriptionHelper.CANCELLED) {
            this.lazySet(SubscriptionHelper.CANCELLED);
            this.parent.onTimeout(this.idx);
         }
      }

      public void onError(Throwable var1) {
         if (this.get() != SubscriptionHelper.CANCELLED) {
            this.lazySet(SubscriptionHelper.CANCELLED);
            this.parent.onTimeoutError(this.idx, var1);
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      public void onNext(Object var1) {
         var1 = (Subscription)this.get();
         if (var1 != SubscriptionHelper.CANCELLED) {
            var1.cancel();
            this.lazySet(SubscriptionHelper.CANCELLED);
            this.parent.onTimeout(this.idx);
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         SubscriptionHelper.setOnce(this, var1, Long.MAX_VALUE);
      }
   }

   static final class TimeoutFallbackSubscriber<T> extends SubscriptionArbiter implements FlowableSubscriber<T>, FlowableTimeout.TimeoutSelectorSupport {
      private static final long serialVersionUID = 3764492702657003550L;
      long consumed;
      final Subscriber<? super T> downstream;
      Publisher<? extends T> fallback;
      final AtomicLong index;
      final Function<? super T, ? extends Publisher<?>> itemTimeoutIndicator;
      final SequentialDisposable task;
      final AtomicReference<Subscription> upstream;

      TimeoutFallbackSubscriber(Subscriber<? super T> var1, Function<? super T, ? extends Publisher<?>> var2, Publisher<? extends T> var3) {
         super(true);
         this.downstream = var1;
         this.itemTimeoutIndicator = var2;
         this.task = new SequentialDisposable();
         this.upstream = new AtomicReference<>();
         this.fallback = var3;
         this.index = new AtomicLong();
      }

      @Override
      public void cancel() {
         super.cancel();
         this.task.dispose();
      }

      public void onComplete() {
         if (this.index.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
            this.task.dispose();
            this.downstream.onComplete();
            this.task.dispose();
         }
      }

      public void onError(Throwable var1) {
         if (this.index.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
            this.task.dispose();
            this.downstream.onError(var1);
            this.task.dispose();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         long var2 = this.index.get();
         if (var2 != Long.MAX_VALUE) {
            AtomicLong var6 = this.index;
            long var4 = var2 + 1L;
            if (var6.compareAndSet(var2, var4)) {
               Disposable var10 = this.task.get();
               if (var10 != null) {
                  var10.dispose();
               }

               this.consumed++;
               this.downstream.onNext(var1);

               try {
                  var11 = ObjectHelper.requireNonNull(this.itemTimeoutIndicator.apply((T)var1), "The itemTimeoutIndicator returned a null Publisher.");
               } catch (Throwable var8) {
                  Exceptions.throwIfFatal(var8);
                  this.upstream.get().cancel();
                  this.index.getAndSet(Long.MAX_VALUE);
                  this.downstream.onError(var8);
                  return;
               }

               var1 = new FlowableTimeout.TimeoutConsumer(var4, this);
               if (this.task.replace(var1)) {
                  var11.subscribe(var1);
               }

               return;
            }
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.setOnce(this.upstream, var1)) {
            this.setSubscription(var1);
         }
      }

      @Override
      public void onTimeout(long var1) {
         if (this.index.compareAndSet(var1, Long.MAX_VALUE)) {
            SubscriptionHelper.cancel(this.upstream);
            Publisher var3 = this.fallback;
            this.fallback = null;
            var1 = this.consumed;
            if (var1 != 0L) {
               this.produced(var1);
            }

            var3.subscribe(new FlowableTimeoutTimed.FallbackSubscriber(this.downstream, this));
         }
      }

      @Override
      public void onTimeoutError(long var1, Throwable var3) {
         if (this.index.compareAndSet(var1, Long.MAX_VALUE)) {
            SubscriptionHelper.cancel(this.upstream);
            this.downstream.onError(var3);
         } else {
            RxJavaPlugins.onError(var3);
         }
      }

      void startFirstTimeout(Publisher<?> var1) {
         if (var1 != null) {
            FlowableTimeout.TimeoutConsumer var2 = new FlowableTimeout.TimeoutConsumer(0L, this);
            if (this.task.replace(var2)) {
               var1.subscribe(var2);
            }
         }
      }
   }

   interface TimeoutSelectorSupport extends FlowableTimeoutTimed.TimeoutSupport {
      void onTimeoutError(long var1, Throwable var3);
   }

   static final class TimeoutSubscriber<T> extends AtomicLong implements FlowableSubscriber<T>, Subscription, FlowableTimeout.TimeoutSelectorSupport {
      private static final long serialVersionUID = 3764492702657003550L;
      final Subscriber<? super T> downstream;
      final Function<? super T, ? extends Publisher<?>> itemTimeoutIndicator;
      final AtomicLong requested;
      final SequentialDisposable task;
      final AtomicReference<Subscription> upstream;

      TimeoutSubscriber(Subscriber<? super T> var1, Function<? super T, ? extends Publisher<?>> var2) {
         this.downstream = var1;
         this.itemTimeoutIndicator = var2;
         this.task = new SequentialDisposable();
         this.upstream = new AtomicReference<>();
         this.requested = new AtomicLong();
      }

      public void cancel() {
         SubscriptionHelper.cancel(this.upstream);
         this.task.dispose();
      }

      public void onComplete() {
         if (this.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
            this.task.dispose();
            this.downstream.onComplete();
         }
      }

      public void onError(Throwable var1) {
         if (this.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
            this.task.dispose();
            this.downstream.onError(var1);
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         long var4 = this.get();
         if (var4 != Long.MAX_VALUE) {
            long var2 = 1L + var4;
            if (this.compareAndSet(var4, var2)) {
               Disposable var6 = this.task.get();
               if (var6 != null) {
                  var6.dispose();
               }

               this.downstream.onNext(var1);

               try {
                  var1 = ObjectHelper.requireNonNull(this.itemTimeoutIndicator.apply((T)var1), "The itemTimeoutIndicator returned a null Publisher.");
               } catch (Throwable var8) {
                  Exceptions.throwIfFatal(var8);
                  this.upstream.get().cancel();
                  this.getAndSet(Long.MAX_VALUE);
                  this.downstream.onError(var8);
                  return;
               }

               FlowableTimeout.TimeoutConsumer var10 = new FlowableTimeout.TimeoutConsumer(var2, this);
               if (this.task.replace(var10)) {
                  var1.subscribe(var10);
               }

               return;
            }
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         SubscriptionHelper.deferredSetOnce(this.upstream, this.requested, var1);
      }

      @Override
      public void onTimeout(long var1) {
         if (this.compareAndSet(var1, Long.MAX_VALUE)) {
            SubscriptionHelper.cancel(this.upstream);
            this.downstream.onError(new TimeoutException());
         }
      }

      @Override
      public void onTimeoutError(long var1, Throwable var3) {
         if (this.compareAndSet(var1, Long.MAX_VALUE)) {
            SubscriptionHelper.cancel(this.upstream);
            this.downstream.onError(var3);
         } else {
            RxJavaPlugins.onError(var3);
         }
      }

      public void request(long var1) {
         SubscriptionHelper.deferredRequest(this.upstream, this.requested, var1);
      }

      void startFirstTimeout(Publisher<?> var1) {
         if (var1 != null) {
            FlowableTimeout.TimeoutConsumer var2 = new FlowableTimeout.TimeoutConsumer(0L, this);
            if (this.task.replace(var2)) {
               var1.subscribe(var2);
            }
         }
      }
   }
}
