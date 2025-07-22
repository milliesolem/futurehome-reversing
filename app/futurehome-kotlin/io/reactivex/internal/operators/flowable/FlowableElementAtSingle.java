package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;
import org.reactivestreams.Subscription;

public final class FlowableElementAtSingle<T> extends Single<T> implements FuseToFlowable<T> {
   final T defaultValue;
   final long index;
   final Flowable<T> source;

   public FlowableElementAtSingle(Flowable<T> var1, long var2, T var4) {
      this.source = var1;
      this.index = var2;
      this.defaultValue = (T)var4;
   }

   @Override
   public Flowable<T> fuseToFlowable() {
      return RxJavaPlugins.onAssembly(new FlowableElementAt<>(this.source, this.index, this.defaultValue, true));
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      this.source.subscribe(new FlowableElementAtSingle.ElementAtSubscriber<>(var1, this.index, this.defaultValue));
   }

   static final class ElementAtSubscriber<T> implements FlowableSubscriber<T>, Disposable {
      long count;
      final T defaultValue;
      boolean done;
      final SingleObserver<? super T> downstream;
      final long index;
      Subscription upstream;

      ElementAtSubscriber(SingleObserver<? super T> var1, long var2, T var4) {
         this.downstream = var1;
         this.index = var2;
         this.defaultValue = (T)var4;
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
         if (!this.done) {
            this.done = true;
            Object var1 = this.defaultValue;
            if (var1 != null) {
               this.downstream.onSuccess((T)var1);
            } else {
               this.downstream.onError(new NoSuchElementException());
            }
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

      public void onNext(T var1) {
         if (!this.done) {
            long var2 = this.count;
            if (var2 == this.index) {
               this.done = true;
               this.upstream.cancel();
               this.upstream = SubscriptionHelper.CANCELLED;
               this.downstream.onSuccess((T)var1);
            } else {
               this.count = var2 + 1L;
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
