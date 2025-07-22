package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.ArrayListSupplier;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscription;

public final class FlowableToListSingle<T, U extends Collection<? super T>> extends Single<U> implements FuseToFlowable<U> {
   final Callable<U> collectionSupplier;
   final Flowable<T> source;

   public FlowableToListSingle(Flowable<T> var1) {
      this(var1, ArrayListSupplier.asCallable());
   }

   public FlowableToListSingle(Flowable<T> var1, Callable<U> var2) {
      this.source = var1;
      this.collectionSupplier = var2;
   }

   @Override
   public Flowable<U> fuseToFlowable() {
      return RxJavaPlugins.onAssembly(new FlowableToList<>(this.source, this.collectionSupplier));
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(SingleObserver<? super U> var1) {
      Collection var2;
      try {
         var2 = ObjectHelper.requireNonNull(
            this.collectionSupplier.call(),
            "The collectionSupplier returned a null collection. Null values are generally not allowed in 2.x operators and sources."
         );
      } catch (Throwable var4) {
         Exceptions.throwIfFatal(var4);
         EmptyDisposable.error(var4, var1);
         return;
      }

      this.source.subscribe(new FlowableToListSingle.ToListSubscriber<>(var1, (U)var2));
   }

   static final class ToListSubscriber<T, U extends Collection<? super T>> implements FlowableSubscriber<T>, Disposable {
      final SingleObserver<? super U> downstream;
      Subscription upstream;
      U value;

      ToListSubscriber(SingleObserver<? super U> var1, U var2) {
         this.downstream = var1;
         this.value = (U)var2;
      }

      @Override
      public void dispose() {
         this.upstream.cancel();
         this.upstream = SubscriptionHelper.CANCELLED;
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.upstream == SubscriptionHelper.CANCELLED) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public void onComplete() {
         this.upstream = SubscriptionHelper.CANCELLED;
         this.downstream.onSuccess(this.value);
      }

      public void onError(Throwable var1) {
         this.value = null;
         this.upstream = SubscriptionHelper.CANCELLED;
         this.downstream.onError(var1);
      }

      public void onNext(T var1) {
         this.value.add((T)var1);
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            var1.request(Long.MAX_VALUE);
         }
      }
   }
}
