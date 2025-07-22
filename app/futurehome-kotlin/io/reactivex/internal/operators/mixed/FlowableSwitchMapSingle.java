package io.reactivex.internal.operators.mixed;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSwitchMapSingle<T, R> extends Flowable<R> {
   final boolean delayErrors;
   final Function<? super T, ? extends SingleSource<? extends R>> mapper;
   final Flowable<T> source;

   public FlowableSwitchMapSingle(Flowable<T> var1, Function<? super T, ? extends SingleSource<? extends R>> var2, boolean var3) {
      this.source = var1;
      this.mapper = var2;
      this.delayErrors = var3;
   }

   @Override
   protected void subscribeActual(Subscriber<? super R> var1) {
      this.source.subscribe(new FlowableSwitchMapSingle.SwitchMapSingleSubscriber<>(var1, this.mapper, this.delayErrors));
   }

   static final class SwitchMapSingleSubscriber<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
      static final FlowableSwitchMapSingle.SwitchMapSingleSubscriber.SwitchMapSingleObserver<Object> INNER_DISPOSED = new FlowableSwitchMapSingle.SwitchMapSingleSubscriber.SwitchMapSingleObserver<>(
         null
      );
      private static final long serialVersionUID = -5402190102429853762L;
      volatile boolean cancelled;
      final boolean delayErrors;
      volatile boolean done;
      final Subscriber<? super R> downstream;
      long emitted;
      final AtomicThrowable errors;
      final AtomicReference<FlowableSwitchMapSingle.SwitchMapSingleSubscriber.SwitchMapSingleObserver<R>> inner;
      final Function<? super T, ? extends SingleSource<? extends R>> mapper;
      final AtomicLong requested;
      Subscription upstream;

      SwitchMapSingleSubscriber(Subscriber<? super R> var1, Function<? super T, ? extends SingleSource<? extends R>> var2, boolean var3) {
         this.downstream = var1;
         this.mapper = var2;
         this.delayErrors = var3;
         this.errors = new AtomicThrowable();
         this.requested = new AtomicLong();
         this.inner = new AtomicReference<>();
      }

      public void cancel() {
         this.cancelled = true;
         this.upstream.cancel();
         this.disposeInner();
      }

      void disposeInner() {
         AtomicReference var2 = this.inner;
         FlowableSwitchMapSingle.SwitchMapSingleSubscriber.SwitchMapSingleObserver var1 = INNER_DISPOSED;
         FlowableSwitchMapSingle.SwitchMapSingleSubscriber.SwitchMapSingleObserver var3 = var2.getAndSet(var1);
         if (var3 != null && var3 != var1) {
            var3.dispose();
         }
      }

      void drain() {
         if (this.getAndIncrement() == 0) {
            Subscriber var6 = this.downstream;
            AtomicThrowable var8 = this.errors;
            AtomicReference var9 = this.inner;
            AtomicLong var7 = this.requested;
            long var3 = this.emitted;
            int var1 = 1;

            while (!this.cancelled) {
               if (var8.get() != null && !this.delayErrors) {
                  var6.onError(var8.terminate());
                  return;
               }

               boolean var5 = this.done;
               FlowableSwitchMapSingle.SwitchMapSingleSubscriber.SwitchMapSingleObserver var10 = (FlowableSwitchMapSingle.SwitchMapSingleSubscriber.SwitchMapSingleObserver)var9.get();
               boolean var2;
               if (var10 == null) {
                  var2 = 1;
               } else {
                  var2 = 0;
               }

               if (var5 && var2) {
                  Throwable var12 = var8.terminate();
                  if (var12 != null) {
                     var6.onError(var12);
                  } else {
                     var6.onComplete();
                  }

                  return;
               }

               if (!var2 && var10.item != null && var3 != var7.get()) {
                  ExternalSyntheticBackportWithForwarding0.m(var9, var10, null);
                  var6.onNext(var10.item);
                  var3++;
               } else {
                  this.emitted = var3;
                  var2 = this.addAndGet(-var1);
                  var1 = var2;
                  if (var2 == 0) {
                     return;
                  }
               }
            }
         }
      }

      void innerError(FlowableSwitchMapSingle.SwitchMapSingleSubscriber.SwitchMapSingleObserver<R> var1, Throwable var2) {
         if (ExternalSyntheticBackportWithForwarding0.m(this.inner, var1, null) && this.errors.addThrowable(var2)) {
            if (!this.delayErrors) {
               this.upstream.cancel();
               this.disposeInner();
            }

            this.drain();
         } else {
            RxJavaPlugins.onError(var2);
         }
      }

      public void onComplete() {
         this.done = true;
         this.drain();
      }

      public void onError(Throwable var1) {
         if (this.errors.addThrowable(var1)) {
            if (!this.delayErrors) {
               this.disposeInner();
            }

            this.done = true;
            this.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         FlowableSwitchMapSingle.SwitchMapSingleSubscriber.SwitchMapSingleObserver var2 = this.inner.get();
         if (var2 != null) {
            var2.dispose();
         }

         try {
            var7 = ObjectHelper.requireNonNull(this.mapper.apply((T)var1), "The mapper returned a null SingleSource");
         } catch (Throwable var5) {
            Exceptions.throwIfFatal(var5);
            this.upstream.cancel();
            this.inner.getAndSet((FlowableSwitchMapSingle.SwitchMapSingleSubscriber.SwitchMapSingleObserver<R>)INNER_DISPOSED);
            this.onError(var5);
            return;
         }

         FlowableSwitchMapSingle.SwitchMapSingleSubscriber.SwitchMapSingleObserver var3 = new FlowableSwitchMapSingle.SwitchMapSingleSubscriber.SwitchMapSingleObserver<>(
            this
         );

         while (true) {
            var1 = this.inner.get();
            if (var1 == INNER_DISPOSED) {
               break;
            }

            if (ExternalSyntheticBackportWithForwarding0.m(this.inner, var1, var3)) {
               var7.subscribe(var3);
               break;
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
         BackpressureHelper.add(this.requested, var1);
         this.drain();
      }

      static final class SwitchMapSingleObserver<R> extends AtomicReference<Disposable> implements SingleObserver<R> {
         private static final long serialVersionUID = 8042919737683345351L;
         volatile R item;
         final FlowableSwitchMapSingle.SwitchMapSingleSubscriber<?, R> parent;

         SwitchMapSingleObserver(FlowableSwitchMapSingle.SwitchMapSingleSubscriber<?, R> var1) {
            this.parent = var1;
         }

         void dispose() {
            DisposableHelper.dispose(this);
         }

         @Override
         public void onError(Throwable var1) {
            this.parent.innerError(this, var1);
         }

         @Override
         public void onSubscribe(Disposable var1) {
            DisposableHelper.setOnce(this, var1);
         }

         @Override
         public void onSuccess(R var1) {
            this.item = (R)var1;
            this.parent.drain();
         }
      }
   }
}
