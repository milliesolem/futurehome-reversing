package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.UnicastProcessor;
import io.reactivex.subscribers.SerializedSubscriber;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableRetryWhen<T> extends AbstractFlowableWithUpstream<T, T> {
   final Function<? super Flowable<Throwable>, ? extends Publisher<?>> handler;

   public FlowableRetryWhen(Flowable<T> var1, Function<? super Flowable<Throwable>, ? extends Publisher<?>> var2) {
      super(var1);
      this.handler = var2;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void subscribeActual(Subscriber<? super T> var1) {
      SerializedSubscriber var5 = new SerializedSubscriber(var1);
      FlowableProcessor var4 = UnicastProcessor.create(8).toSerialized();

      Publisher var2;
      try {
         var2 = ObjectHelper.requireNonNull(this.handler.apply(var4), "handler returned a null Publisher");
      } catch (Throwable var7) {
         Exceptions.throwIfFatal(var7);
         EmptySubscription.error(var7, var1);
         return;
      }

      FlowableRepeatWhen.WhenReceiver var3 = new FlowableRepeatWhen.WhenReceiver(this.source);
      FlowableRetryWhen.RetryWhenSubscriber var8 = new FlowableRetryWhen.RetryWhenSubscriber(var5, var4, var3);
      var3.subscriber = var8;
      var1.onSubscribe(var8);
      var2.subscribe(var3);
      var3.onNext(0);
   }

   static final class RetryWhenSubscriber<T> extends FlowableRepeatWhen.WhenSourceSubscriber<T, Throwable> {
      private static final long serialVersionUID = -2680129890138081029L;

      RetryWhenSubscriber(Subscriber<? super T> var1, FlowableProcessor<Throwable> var2, Subscription var3) {
         super(var1, var2, var3);
      }

      public void onComplete() {
         this.receiver.cancel();
         this.downstream.onComplete();
      }

      public void onError(Throwable var1) {
         this.again(var1);
      }
   }
}
