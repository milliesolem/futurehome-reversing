package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableDoFinally<T> extends AbstractFlowableWithUpstream<T, T> {
   final Action onFinally;

   public FlowableDoFinally(Flowable<T> var1, Action var2) {
      super(var1);
      this.onFinally = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      if (var1 instanceof ConditionalSubscriber) {
         this.source.subscribe(new FlowableDoFinally.DoFinallyConditionalSubscriber<>((ConditionalSubscriber<? super T>)var1, this.onFinally));
      } else {
         this.source.subscribe(new FlowableDoFinally.DoFinallySubscriber<>(var1, this.onFinally));
      }
   }

   static final class DoFinallyConditionalSubscriber<T> extends BasicIntQueueSubscription<T> implements ConditionalSubscriber<T> {
      private static final long serialVersionUID = 4109457741734051389L;
      final ConditionalSubscriber<? super T> downstream;
      final Action onFinally;
      QueueSubscription<T> qs;
      boolean syncFused;
      Subscription upstream;

      DoFinallyConditionalSubscriber(ConditionalSubscriber<? super T> var1, Action var2) {
         this.downstream = var1;
         this.onFinally = var2;
      }

      public void cancel() {
         this.upstream.cancel();
         this.runFinally();
      }

      @Override
      public void clear() {
         this.qs.clear();
      }

      @Override
      public boolean isEmpty() {
         return this.qs.isEmpty();
      }

      public void onComplete() {
         this.downstream.onComplete();
         this.runFinally();
      }

      public void onError(Throwable var1) {
         this.downstream.onError(var1);
         this.runFinally();
      }

      public void onNext(T var1) {
         this.downstream.onNext(var1);
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            if (var1 instanceof QueueSubscription) {
               this.qs = (QueueSubscription<T>)var1;
            }

            this.downstream.onSubscribe(this);
         }
      }

      @Override
      public T poll() throws Exception {
         Object var1 = this.qs.poll();
         if (var1 == null && this.syncFused) {
            this.runFinally();
         }

         return (T)var1;
      }

      public void request(long var1) {
         this.upstream.request(var1);
      }

      @Override
      public int requestFusion(int var1) {
         QueueSubscription var3 = this.qs;
         boolean var2 = false;
         if (var3 != null && (var1 & 4) == 0) {
            var1 = var3.requestFusion(var1);
            if (var1 != 0) {
               if (var1 == 1) {
                  var2 = true;
               }

               this.syncFused = var2;
            }

            return var1;
         } else {
            return 0;
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void runFinally() {
         if (this.compareAndSet(0, 1)) {
            try {
               this.onFinally.run();
            } catch (Throwable var3) {
               Exceptions.throwIfFatal(var3);
               RxJavaPlugins.onError(var3);
               return;
            }
         }
      }

      @Override
      public boolean tryOnNext(T var1) {
         return this.downstream.tryOnNext((T)var1);
      }
   }

   static final class DoFinallySubscriber<T> extends BasicIntQueueSubscription<T> implements FlowableSubscriber<T> {
      private static final long serialVersionUID = 4109457741734051389L;
      final Subscriber<? super T> downstream;
      final Action onFinally;
      QueueSubscription<T> qs;
      boolean syncFused;
      Subscription upstream;

      DoFinallySubscriber(Subscriber<? super T> var1, Action var2) {
         this.downstream = var1;
         this.onFinally = var2;
      }

      public void cancel() {
         this.upstream.cancel();
         this.runFinally();
      }

      @Override
      public void clear() {
         this.qs.clear();
      }

      @Override
      public boolean isEmpty() {
         return this.qs.isEmpty();
      }

      public void onComplete() {
         this.downstream.onComplete();
         this.runFinally();
      }

      public void onError(Throwable var1) {
         this.downstream.onError(var1);
         this.runFinally();
      }

      public void onNext(T var1) {
         this.downstream.onNext(var1);
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            if (var1 instanceof QueueSubscription) {
               this.qs = (QueueSubscription<T>)var1;
            }

            this.downstream.onSubscribe(this);
         }
      }

      @Override
      public T poll() throws Exception {
         Object var1 = this.qs.poll();
         if (var1 == null && this.syncFused) {
            this.runFinally();
         }

         return (T)var1;
      }

      public void request(long var1) {
         this.upstream.request(var1);
      }

      @Override
      public int requestFusion(int var1) {
         QueueSubscription var3 = this.qs;
         boolean var2 = false;
         if (var3 != null && (var1 & 4) == 0) {
            var1 = var3.requestFusion(var1);
            if (var1 != 0) {
               if (var1 == 1) {
                  var2 = true;
               }

               this.syncFused = var2;
            }

            return var1;
         } else {
            return 0;
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void runFinally() {
         if (this.compareAndSet(0, 1)) {
            try {
               this.onFinally.run();
            } catch (Throwable var3) {
               Exceptions.throwIfFatal(var3);
               RxJavaPlugins.onError(var3);
               return;
            }
         }
      }
   }
}
