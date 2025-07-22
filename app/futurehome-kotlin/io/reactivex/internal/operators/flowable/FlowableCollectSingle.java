package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiConsumer;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscription;

public final class FlowableCollectSingle<T, U> extends Single<U> implements FuseToFlowable<U> {
   final BiConsumer<? super U, ? super T> collector;
   final Callable<? extends U> initialSupplier;
   final Flowable<T> source;

   public FlowableCollectSingle(Flowable<T> var1, Callable<? extends U> var2, BiConsumer<? super U, ? super T> var3) {
      this.source = var1;
      this.initialSupplier = var2;
      this.collector = var3;
   }

   @Override
   public Flowable<U> fuseToFlowable() {
      return RxJavaPlugins.onAssembly(new FlowableCollect<>(this.source, this.initialSupplier, this.collector));
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(SingleObserver<? super U> var1) {
      Object var2;
      try {
         var2 = ObjectHelper.requireNonNull(this.initialSupplier.call(), "The initialSupplier returned a null value");
      } catch (Throwable var4) {
         EmptyDisposable.error(var4, var1);
         return;
      }

      this.source.subscribe(new FlowableCollectSingle.CollectSubscriber<>(var1, var2, this.collector));
   }

   static final class CollectSubscriber<T, U> implements FlowableSubscriber<T>, Disposable {
      final BiConsumer<? super U, ? super T> collector;
      boolean done;
      final SingleObserver<? super U> downstream;
      final U u;
      Subscription upstream;

      CollectSubscriber(SingleObserver<? super U> var1, U var2, BiConsumer<? super U, ? super T> var3) {
         this.downstream = var1;
         this.collector = var3;
         this.u = (U)var2;
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
         if (!this.done) {
            this.done = true;
            this.upstream = SubscriptionHelper.CANCELLED;
            this.downstream.onSuccess(this.u);
         }
      }

      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.upstream = SubscriptionHelper.CANCELLED;
            this.downstream.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         if (!this.done) {
            try {
               this.collector.accept(this.u, (T)var1);
            } catch (Throwable var3) {
               Exceptions.throwIfFatal(var3);
               this.upstream.cancel();
               this.onError(var3);
               return;
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
   }
}
