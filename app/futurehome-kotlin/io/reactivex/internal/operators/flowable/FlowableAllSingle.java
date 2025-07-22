package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscription;

public final class FlowableAllSingle<T> extends Single<Boolean> implements FuseToFlowable<Boolean> {
   final Predicate<? super T> predicate;
   final Flowable<T> source;

   public FlowableAllSingle(Flowable<T> var1, Predicate<? super T> var2) {
      this.source = var1;
      this.predicate = var2;
   }

   @Override
   public Flowable<Boolean> fuseToFlowable() {
      return RxJavaPlugins.onAssembly(new FlowableAll<>(this.source, this.predicate));
   }

   @Override
   protected void subscribeActual(SingleObserver<? super Boolean> var1) {
      this.source.subscribe(new FlowableAllSingle.AllSubscriber<>(var1, this.predicate));
   }

   static final class AllSubscriber<T> implements FlowableSubscriber<T>, Disposable {
      boolean done;
      final SingleObserver<? super Boolean> downstream;
      final Predicate<? super T> predicate;
      Subscription upstream;

      AllSubscriber(SingleObserver<? super Boolean> var1, Predicate<? super T> var2) {
         this.downstream = var1;
         this.predicate = var2;
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
            this.downstream.onSuccess(true);
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
            boolean var2;
            try {
               var2 = this.predicate.test((T)var1);
            } catch (Throwable var4) {
               Exceptions.throwIfFatal(var4);
               this.upstream.cancel();
               this.upstream = SubscriptionHelper.CANCELLED;
               this.onError(var4);
               return;
            }

            if (!var2) {
               this.done = true;
               this.upstream.cancel();
               this.upstream = SubscriptionHelper.CANCELLED;
               this.downstream.onSuccess(false);
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
