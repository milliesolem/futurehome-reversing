package io.reactivex.internal.subscribers;

import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscription;

public abstract class BasicFuseableConditionalSubscriber<T, R> implements ConditionalSubscriber<T>, QueueSubscription<R> {
   protected boolean done;
   protected final ConditionalSubscriber<? super R> downstream;
   protected QueueSubscription<T> qs;
   protected int sourceMode;
   protected Subscription upstream;

   public BasicFuseableConditionalSubscriber(ConditionalSubscriber<? super R> var1) {
      this.downstream = var1;
   }

   protected void afterDownstream() {
   }

   protected boolean beforeDownstream() {
      return true;
   }

   public void cancel() {
      this.upstream.cancel();
   }

   @Override
   public void clear() {
      this.qs.clear();
   }

   protected final void fail(Throwable var1) {
      Exceptions.throwIfFatal(var1);
      this.upstream.cancel();
      this.onError(var1);
   }

   @Override
   public boolean isEmpty() {
      return this.qs.isEmpty();
   }

   @Override
   public final boolean offer(R var1) {
      throw new UnsupportedOperationException("Should not be called!");
   }

   @Override
   public final boolean offer(R var1, R var2) {
      throw new UnsupportedOperationException("Should not be called!");
   }

   public void onComplete() {
      if (!this.done) {
         this.done = true;
         this.downstream.onComplete();
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

   @Override
   public final void onSubscribe(Subscription var1) {
      if (SubscriptionHelper.validate(this.upstream, var1)) {
         this.upstream = var1;
         if (var1 instanceof QueueSubscription) {
            this.qs = (QueueSubscription<T>)var1;
         }

         if (this.beforeDownstream()) {
            this.downstream.onSubscribe(this);
            this.afterDownstream();
         }
      }
   }

   public void request(long var1) {
      this.upstream.request(var1);
   }

   protected final int transitiveBoundaryFusion(int var1) {
      QueueSubscription var2 = this.qs;
      if (var2 != null && (var1 & 4) == 0) {
         var1 = var2.requestFusion(var1);
         if (var1 != 0) {
            this.sourceMode = var1;
         }

         return var1;
      } else {
         return 0;
      }
   }
}
