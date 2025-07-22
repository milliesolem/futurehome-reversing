package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableElementAt<T> extends AbstractFlowableWithUpstream<T, T> {
   final T defaultValue;
   final boolean errorOnFewer;
   final long index;

   public FlowableElementAt(Flowable<T> var1, long var2, T var4, boolean var5) {
      super(var1);
      this.index = var2;
      this.defaultValue = (T)var4;
      this.errorOnFewer = var5;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source.subscribe(new FlowableElementAt.ElementAtSubscriber<>(var1, this.index, this.defaultValue, this.errorOnFewer));
   }

   static final class ElementAtSubscriber<T> extends DeferredScalarSubscription<T> implements FlowableSubscriber<T> {
      private static final long serialVersionUID = 4066607327284737757L;
      long count;
      final T defaultValue;
      boolean done;
      final boolean errorOnFewer;
      final long index;
      Subscription upstream;

      ElementAtSubscriber(Subscriber<? super T> var1, long var2, T var4, boolean var5) {
         super(var1);
         this.index = var2;
         this.defaultValue = (T)var4;
         this.errorOnFewer = var5;
      }

      @Override
      public void cancel() {
         super.cancel();
         this.upstream.cancel();
      }

      public void onComplete() {
         if (!this.done) {
            this.done = true;
            Object var1 = this.defaultValue;
            if (var1 == null) {
               if (this.errorOnFewer) {
                  this.downstream.onError(new NoSuchElementException());
               } else {
                  this.downstream.onComplete();
               }
            } else {
               this.complete((T)var1);
            }
         }
      }

      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.downstream.onError(var1);
         }
      }

      public void onNext(T var1) {
         if (!this.done) {
            long var2 = this.count;
            if (var2 == this.index) {
               this.done = true;
               this.upstream.cancel();
               this.complete((T)var1);
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
