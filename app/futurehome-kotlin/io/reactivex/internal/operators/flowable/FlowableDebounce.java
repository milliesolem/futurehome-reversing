package io.reactivex.internal.operators.flowable;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subscribers.DisposableSubscriber;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableDebounce<T, U> extends AbstractFlowableWithUpstream<T, T> {
   final Function<? super T, ? extends Publisher<U>> debounceSelector;

   public FlowableDebounce(Flowable<T> var1, Function<? super T, ? extends Publisher<U>> var2) {
      super(var1);
      this.debounceSelector = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source.subscribe(new FlowableDebounce.DebounceSubscriber<>(new SerializedSubscriber<>(var1), this.debounceSelector));
   }

   static final class DebounceSubscriber<T, U> extends AtomicLong implements FlowableSubscriber<T>, Subscription {
      private static final long serialVersionUID = 6725975399620862591L;
      final Function<? super T, ? extends Publisher<U>> debounceSelector;
      final AtomicReference<Disposable> debouncer = new AtomicReference<>();
      boolean done;
      final Subscriber<? super T> downstream;
      volatile long index;
      Subscription upstream;

      DebounceSubscriber(Subscriber<? super T> var1, Function<? super T, ? extends Publisher<U>> var2) {
         this.downstream = var1;
         this.debounceSelector = var2;
      }

      public void cancel() {
         this.upstream.cancel();
         DisposableHelper.dispose(this.debouncer);
      }

      void emit(long var1, T var3) {
         if (var1 == this.index) {
            if (this.get() != 0L) {
               this.downstream.onNext(var3);
               BackpressureHelper.produced(this, 1L);
            } else {
               this.cancel();
               this.downstream.onError(new MissingBackpressureException("Could not deliver value due to lack of requests"));
            }
         }
      }

      public void onComplete() {
         if (!this.done) {
            this.done = true;
            Disposable var1 = this.debouncer.get();
            if (!DisposableHelper.isDisposed(var1)) {
               FlowableDebounce.DebounceSubscriber.DebounceInnerSubscriber var2 = (FlowableDebounce.DebounceSubscriber.DebounceInnerSubscriber)var1;
               if (var2 != null) {
                  var2.emit();
               }

               DisposableHelper.dispose(this.debouncer);
               this.downstream.onComplete();
            }
         }
      }

      public void onError(Throwable var1) {
         DisposableHelper.dispose(this.debouncer);
         this.downstream.onError(var1);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         if (!this.done) {
            long var2 = this.index + 1L;
            this.index = var2;
            Disposable var4 = this.debouncer.get();
            if (var4 != null) {
               var4.dispose();
            }

            Publisher var5;
            try {
               var5 = ObjectHelper.requireNonNull(this.debounceSelector.apply((T)var1), "The publisher supplied is null");
            } catch (Throwable var7) {
               Exceptions.throwIfFatal(var7);
               this.cancel();
               this.downstream.onError(var7);
               return;
            }

            var1 = new FlowableDebounce.DebounceSubscriber.DebounceInnerSubscriber<>(this, var2, (T)var1);
            if (ExternalSyntheticBackportWithForwarding0.m(this.debouncer, var4, var1)) {
               var5.subscribe(var1);
            }
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            var1.request(Long.MAX_VALUE);
         }
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            BackpressureHelper.add(this, var1);
         }
      }

      static final class DebounceInnerSubscriber<T, U> extends DisposableSubscriber<U> {
         boolean done;
         final long index;
         final AtomicBoolean once = new AtomicBoolean();
         final FlowableDebounce.DebounceSubscriber<T, U> parent;
         final T value;

         DebounceInnerSubscriber(FlowableDebounce.DebounceSubscriber<T, U> var1, long var2, T var4) {
            this.parent = var1;
            this.index = var2;
            this.value = (T)var4;
         }

         void emit() {
            if (this.once.compareAndSet(false, true)) {
               this.parent.emit(this.index, this.value);
            }
         }

         public void onComplete() {
            if (!this.done) {
               this.done = true;
               this.emit();
            }
         }

         public void onError(Throwable var1) {
            if (this.done) {
               RxJavaPlugins.onError(var1);
            } else {
               this.done = true;
               this.parent.onError(var1);
            }
         }

         public void onNext(U var1) {
            if (!this.done) {
               this.done = true;
               this.cancel();
               this.emit();
            }
         }
      }
   }
}
