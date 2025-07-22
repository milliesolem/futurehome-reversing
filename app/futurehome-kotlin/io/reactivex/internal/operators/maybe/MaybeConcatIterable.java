package io.reactivex.internal.operators.maybe;

import io.reactivex.Flowable;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.NotificationLite;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class MaybeConcatIterable<T> extends Flowable<T> {
   final Iterable<? extends MaybeSource<? extends T>> sources;

   public MaybeConcatIterable(Iterable<? extends MaybeSource<? extends T>> var1) {
      this.sources = var1;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      Iterator var2;
      try {
         var2 = ObjectHelper.requireNonNull(this.sources.iterator(), "The sources Iterable returned a null Iterator");
      } catch (Throwable var4) {
         Exceptions.throwIfFatal(var4);
         EmptySubscription.error(var4, var1);
         return;
      }

      MaybeConcatIterable.ConcatMaybeObserver var5 = new MaybeConcatIterable.ConcatMaybeObserver(var1, var2);
      var1.onSubscribe(var5);
      var5.drain();
   }

   static final class ConcatMaybeObserver<T> extends AtomicInteger implements MaybeObserver<T>, Subscription {
      private static final long serialVersionUID = 3520831347801429610L;
      final AtomicReference<Object> current;
      final SequentialDisposable disposables;
      final Subscriber<? super T> downstream;
      long produced;
      final AtomicLong requested;
      final Iterator<? extends MaybeSource<? extends T>> sources;

      ConcatMaybeObserver(Subscriber<? super T> var1, Iterator<? extends MaybeSource<? extends T>> var2) {
         this.downstream = var1;
         this.sources = var2;
         this.requested = new AtomicLong();
         this.disposables = new SequentialDisposable();
         this.current = new AtomicReference<>(NotificationLite.COMPLETE);
      }

      public void cancel() {
         this.disposables.dispose();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void drain() {
         if (this.getAndIncrement() == 0) {
            AtomicReference var6 = this.current;
            Subscriber var4 = this.downstream;
            SequentialDisposable var5 = this.disposables;

            while (!var5.isDisposed()) {
               MaybeSource var7 = (MaybeSource)var6.get();
               label95:
               if (var7 != null) {
                  if (var7 != NotificationLite.COMPLETE) {
                     long var1 = this.produced;
                     if (var1 == this.requested.get()) {
                        break label95;
                     }

                     this.produced = var1 + 1L;
                     var6.lazySet(null);
                     var4.onNext(var7);
                  } else {
                     var6.lazySet(null);
                  }

                  if (!var5.isDisposed()) {
                     boolean var3;
                     try {
                        var3 = this.sources.hasNext();
                     } catch (Throwable var13) {
                        Exceptions.throwIfFatal(var13);
                        var4.onError(var13);
                        return;
                     }

                     if (var3) {
                        try {
                           var7 = ObjectHelper.requireNonNull(this.sources.next(), "The source Iterator returned a null MaybeSource");
                        } catch (Throwable var12) {
                           Exceptions.throwIfFatal(var12);
                           var4.onError(var12);
                           return;
                        }

                        var7.subscribe(this);
                     } else {
                        var4.onComplete();
                     }
                  }
               }

               if (this.decrementAndGet() == 0) {
                  return;
               }
            }

            var6.lazySet(null);
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
