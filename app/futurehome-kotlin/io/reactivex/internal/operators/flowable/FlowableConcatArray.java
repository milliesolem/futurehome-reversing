package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableConcatArray<T> extends Flowable<T> {
   final boolean delayError;
   final Publisher<? extends T>[] sources;

   public FlowableConcatArray(Publisher<? extends T>[] var1, boolean var2) {
      this.sources = var1;
      this.delayError = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      FlowableConcatArray.ConcatArraySubscriber var2 = new FlowableConcatArray.ConcatArraySubscriber(this.sources, this.delayError, var1);
      var1.onSubscribe(var2);
      var2.onComplete();
   }

   static final class ConcatArraySubscriber<T> extends SubscriptionArbiter implements FlowableSubscriber<T> {
      private static final long serialVersionUID = -8158322871608889516L;
      final boolean delayError;
      final Subscriber<? super T> downstream;
      List<Throwable> errors;
      int index;
      long produced;
      final Publisher<? extends T>[] sources;
      final AtomicInteger wip;

      ConcatArraySubscriber(Publisher<? extends T>[] var1, boolean var2, Subscriber<? super T> var3) {
         super(false);
         this.downstream = var3;
         this.sources = var1;
         this.delayError = var2;
         this.wip = new AtomicInteger();
      }

      public void onComplete() {
         if (this.wip.getAndIncrement() == 0) {
            Publisher[] var7 = this.sources;
            int var2 = var7.length;
            int var1 = this.index;

            while (true) {
               if (var1 == var2) {
                  List var10 = this.errors;
                  if (var10 != null) {
                     if (var10.size() == 1) {
                        this.downstream.onError((Throwable)var10.get(0));
                     } else {
                        this.downstream.onError(new CompositeException(var10));
                     }
                  } else {
                     this.downstream.onComplete();
                  }

                  return;
               }

               Publisher var5 = var7[var1];
               if (var5 == null) {
                  NullPointerException var8 = new NullPointerException("A Publisher entry is null");
                  if (!this.delayError) {
                     this.downstream.onError(var8);
                     return;
                  }

                  List var6 = this.errors;
                  Object var9 = var6;
                  if (var6 == null) {
                     var9 = new ArrayList(var2 - var1 + 1);
                     this.errors = (List<Throwable>)var9;
                  }

                  var9.add(var8);
                  var1++;
               } else {
                  long var3 = this.produced;
                  if (var3 != 0L) {
                     this.produced = 0L;
                     this.produced(var3);
                  }

                  var5.subscribe(this);
                  this.index = ++var1;
                  if (this.wip.decrementAndGet() == 0) {
                     break;
                  }
               }
            }
         }
      }

      public void onError(Throwable var1) {
         if (this.delayError) {
            List var3 = this.errors;
            Object var2 = var3;
            if (var3 == null) {
               var2 = new ArrayList(this.sources.length - this.index + 1);
               this.errors = (List<Throwable>)var2;
            }

            var2.add(var1);
            this.onComplete();
         } else {
            this.downstream.onError(var1);
         }
      }

      public void onNext(T var1) {
         this.produced++;
         this.downstream.onNext(var1);
      }

      @Override
      public void onSubscribe(Subscription var1) {
         this.setSubscription(var1);
      }
   }
}
