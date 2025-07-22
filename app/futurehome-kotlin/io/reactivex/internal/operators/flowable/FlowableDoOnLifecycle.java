package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.LongConsumer;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableDoOnLifecycle<T> extends AbstractFlowableWithUpstream<T, T> {
   private final Action onCancel;
   private final LongConsumer onRequest;
   private final Consumer<? super Subscription> onSubscribe;

   public FlowableDoOnLifecycle(Flowable<T> var1, Consumer<? super Subscription> var2, LongConsumer var3, Action var4) {
      super(var1);
      this.onSubscribe = var2;
      this.onRequest = var3;
      this.onCancel = var4;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source.subscribe(new FlowableDoOnLifecycle.SubscriptionLambdaSubscriber<>(var1, this.onSubscribe, this.onRequest, this.onCancel));
   }

   static final class SubscriptionLambdaSubscriber<T> implements FlowableSubscriber<T>, Subscription {
      final Subscriber<? super T> downstream;
      final Action onCancel;
      final LongConsumer onRequest;
      final Consumer<? super Subscription> onSubscribe;
      Subscription upstream;

      SubscriptionLambdaSubscriber(Subscriber<? super T> var1, Consumer<? super Subscription> var2, LongConsumer var3, Action var4) {
         this.downstream = var1;
         this.onSubscribe = var2;
         this.onCancel = var4;
         this.onRequest = var3;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void cancel() {
         Subscription var2 = this.upstream;
         if (var2 != SubscriptionHelper.CANCELLED) {
            this.upstream = SubscriptionHelper.CANCELLED;

            label24:
            try {
               this.onCancel.run();
            } catch (Throwable var4) {
               Exceptions.throwIfFatal(var4);
               RxJavaPlugins.onError(var4);
               break label24;
            }

            var2.cancel();
         }
      }

      public void onComplete() {
         if (this.upstream != SubscriptionHelper.CANCELLED) {
            this.downstream.onComplete();
         }
      }

      public void onError(Throwable var1) {
         if (this.upstream != SubscriptionHelper.CANCELLED) {
            this.downstream.onError(var1);
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      public void onNext(T var1) {
         this.downstream.onNext(var1);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onSubscribe(Subscription var1) {
         try {
            this.onSubscribe.accept(var1);
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            var1.cancel();
            this.upstream = SubscriptionHelper.CANCELLED;
            EmptySubscription.error(var4, this.downstream);
            return;
         }

         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void request(long var1) {
         label17:
         try {
            this.onRequest.accept(var1);
         } catch (Throwable var5) {
            Exceptions.throwIfFatal(var5);
            RxJavaPlugins.onError(var5);
            break label17;
         }

         this.upstream.request(var1);
      }
   }
}
