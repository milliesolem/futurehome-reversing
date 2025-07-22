package io.reactivex.internal.operators.maybe;

import io.reactivex.Flowable;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.NotificationLite;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class MaybeConcatArray<T> extends Flowable<T> {
   final MaybeSource<? extends T>[] sources;

   public MaybeConcatArray(MaybeSource<? extends T>[] var1) {
      this.sources = var1;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      MaybeConcatArray.ConcatMaybeObserver var2 = new MaybeConcatArray.ConcatMaybeObserver<>(var1, this.sources);
      var1.onSubscribe(var2);
      var2.drain();
   }

   static final class ConcatMaybeObserver<T> extends AtomicInteger implements MaybeObserver<T>, Subscription {
      private static final long serialVersionUID = 3520831347801429610L;
      final AtomicReference<Object> current;
      final SequentialDisposable disposables;
      final Subscriber<? super T> downstream;
      int index;
      long produced;
      final AtomicLong requested;
      final MaybeSource<? extends T>[] sources;

      ConcatMaybeObserver(Subscriber<? super T> var1, MaybeSource<? extends T>[] var2) {
         this.downstream = var1;
         this.sources = var2;
         this.requested = new AtomicLong();
         this.disposables = new SequentialDisposable();
         this.current = new AtomicReference<>(NotificationLite.COMPLETE);
      }

      public void cancel() {
         this.disposables.dispose();
      }

      void drain() {
         if (this.getAndIncrement() == 0) {
            AtomicReference var5 = this.current;
            Subscriber var4 = this.downstream;
            SequentialDisposable var6 = this.disposables;

            while (!var6.isDisposed()) {
               Object var7 = var5.get();
               label33:
               if (var7 != null) {
                  if (var7 != NotificationLite.COMPLETE) {
                     long var2 = this.produced;
                     if (var2 == this.requested.get()) {
                        break label33;
                     }

                     this.produced = var2 + 1L;
                     var5.lazySet(null);
                     var4.onNext(var7);
                  } else {
                     var5.lazySet(null);
                  }

                  if (!var6.isDisposed()) {
                     int var1 = this.index;
                     var7 = this.sources;
                     if (var1 == ((Object[])var7).length) {
                        var4.onComplete();
                        return;
                     }

                     this.index = var1 + 1;
                     ((MaybeSource<T>)((Object[])var7)[var1]).subscribe(this);
                  }
               }

               if (this.decrementAndGet() == 0) {
                  return;
               }
            }

            var5.lazySet(null);
         }
      }

      @Override
      public void onComplete() {
         this.current.lazySet(NotificationLite.COMPLETE);
         this.drain();
      }

      @Override
      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         this.disposables.replace(var1);
      }

      @Override
      public void onSuccess(T var1) {
         this.current.lazySet(var1);
         this.drain();
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            BackpressureHelper.add(this.requested, var1);
            this.drain();
         }
      }
   }
}
