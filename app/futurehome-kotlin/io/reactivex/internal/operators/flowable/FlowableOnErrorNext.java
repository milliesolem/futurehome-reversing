package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableOnErrorNext<T> extends AbstractFlowableWithUpstream<T, T> {
   final boolean allowFatal;
   final Function<? super Throwable, ? extends Publisher<? extends T>> nextSupplier;

   public FlowableOnErrorNext(Flowable<T> var1, Function<? super Throwable, ? extends Publisher<? extends T>> var2, boolean var3) {
      super(var1);
      this.nextSupplier = var2;
      this.allowFatal = var3;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      FlowableOnErrorNext.OnErrorNextSubscriber var2 = new FlowableOnErrorNext.OnErrorNextSubscriber<>(var1, this.nextSupplier, this.allowFatal);
      var1.onSubscribe(var2);
      this.source.subscribe(var2);
   }

   static final class OnErrorNextSubscriber<T> extends SubscriptionArbiter implements FlowableSubscriber<T> {
      private static final long serialVersionUID = 4063763155303814625L;
      final boolean allowFatal;
      boolean done;
      final Subscriber<? super T> downstream;
      final Function<? super Throwable, ? extends Publisher<? extends T>> nextSupplier;
      boolean once;
      long produced;

      OnErrorNextSubscriber(Subscriber<? super T> var1, Function<? super Throwable, ? extends Publisher<? extends T>> var2, boolean var3) {
         super(false);
         this.downstream = var1;
         this.nextSupplier = var2;
         this.allowFatal = var3;
      }

      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.once = true;
            this.downstream.onComplete();
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onError(Throwable var1) {
         if (this.once) {
            if (this.done) {
               RxJavaPlugins.onError(var1);
            } else {
               this.downstream.onError(var1);
            }
         } else {
            this.once = true;
            if (this.allowFatal && !(var1 instanceof Exception)) {
               this.downstream.onError(var1);
            } else {
               Publisher var4;
               try {
                  var4 = ObjectHelper.requireNonNull(this.nextSupplier.apply(var1), "The nextSupplier returned a null Publisher");
               } catch (Throwable var6) {
                  Exceptions.throwIfFatal(var6);
                  this.downstream.onError(new CompositeException(var1, var6));
                  return;
               }

               long var2 = this.produced;
               if (var2 != 0L) {
                  this.produced(var2);
               }

               var4.subscribe(this);
            }
         }
      }

      public void onNext(T var1) {
         if (!this.done) {
            if (!this.once) {
               this.produced++;
            }

            this.downstream.onNext(var1);
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         this.setSubscription(var1);
      }
   }
}
