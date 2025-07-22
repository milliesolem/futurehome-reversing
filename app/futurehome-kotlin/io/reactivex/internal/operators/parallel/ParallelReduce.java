package io.reactivex.internal.operators.parallel;

import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscribers.DeferredScalarSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelReduce<T, R> extends ParallelFlowable<R> {
   final Callable<R> initialSupplier;
   final BiFunction<R, ? super T, R> reducer;
   final ParallelFlowable<? extends T> source;

   public ParallelReduce(ParallelFlowable<? extends T> var1, Callable<R> var2, BiFunction<R, ? super T, R> var3) {
      this.source = var1;
      this.initialSupplier = var2;
      this.reducer = var3;
   }

   @Override
   public int parallelism() {
      return this.source.parallelism();
   }

   void reportError(Subscriber<?>[] var1, Throwable var2) {
      int var4 = var1.length;

      for (int var3 = 0; var3 < var4; var3++) {
         EmptySubscription.error(var2, var1[var3]);
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void subscribe(Subscriber<? super R>[] var1) {
      if (this.validate(var1)) {
         int var3 = var1.length;
         Subscriber[] var5 = new Subscriber[var3];

         for (int var2 = 0; var2 < var3; var2++) {
            Object var4;
            try {
               var4 = ObjectHelper.requireNonNull(this.initialSupplier.call(), "The initialSupplier returned a null value");
            } catch (Throwable var7) {
               Exceptions.throwIfFatal(var7);
               this.reportError(var1, var7);
               return;
            }

            var5[var2] = new ParallelReduce.ParallelReduceSubscriber<>(var1[var2], var4, this.reducer);
         }

         this.source.subscribe(var5);
      }
   }

   static final class ParallelReduceSubscriber<T, R> extends DeferredScalarSubscriber<T, R> {
      private static final long serialVersionUID = 8200530050639449080L;
      R accumulator;
      boolean done;
      final BiFunction<R, ? super T, R> reducer;

      ParallelReduceSubscriber(Subscriber<? super R> var1, R var2, BiFunction<R, ? super T, R> var3) {
         super(var1);
         this.accumulator = (R)var2;
         this.reducer = var3;
      }

      @Override
      public void cancel() {
         super.cancel();
         this.upstream.cancel();
      }

      @Override
      public void onComplete() {
         if (!this.done) {
            this.done = true;
            Object var1 = this.accumulator;
            this.accumulator = null;
            this.complete((R)var1);
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.accumulator = null;
            this.downstream.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         if (!this.done) {
            try {
               var1 = ObjectHelper.requireNonNull(this.reducer.apply(this.accumulator, (T)var1), "The reducer returned a null value");
            } catch (Throwable var3) {
               Exceptions.throwIfFatal(var3);
               this.cancel();
               this.onError(var3);
               return;
            }

            this.accumulator = (R)var1;
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
