package io.reactivex.internal.operators.parallel;

import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiConsumer;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscribers.DeferredScalarSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelCollect<T, C> extends ParallelFlowable<C> {
   final BiConsumer<? super C, ? super T> collector;
   final Callable<? extends C> initialCollection;
   final ParallelFlowable<? extends T> source;

   public ParallelCollect(ParallelFlowable<? extends T> var1, Callable<? extends C> var2, BiConsumer<? super C, ? super T> var3) {
      this.source = var1;
      this.initialCollection = var2;
      this.collector = var3;
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
   public void subscribe(Subscriber<? super C>[] var1) {
      if (this.validate(var1)) {
         int var3 = var1.length;
         Subscriber[] var4 = new Subscriber[var3];

         for (int var2 = 0; var2 < var3; var2++) {
            Object var5;
            try {
               var5 = ObjectHelper.requireNonNull(this.initialCollection.call(), "The initialSupplier returned a null value");
            } catch (Throwable var7) {
               Exceptions.throwIfFatal(var7);
               this.reportError(var1, var7);
               return;
            }

            var4[var2] = new ParallelCollect.ParallelCollectSubscriber<>(var1[var2], var5, this.collector);
         }

         this.source.subscribe(var4);
      }
   }

   static final class ParallelCollectSubscriber<T, C> extends DeferredScalarSubscriber<T, C> {
      private static final long serialVersionUID = -4767392946044436228L;
      C collection;
      final BiConsumer<? super C, ? super T> collector;
      boolean done;

      ParallelCollectSubscriber(Subscriber<? super C> var1, C var2, BiConsumer<? super C, ? super T> var3) {
         super(var1);
         this.collection = (C)var2;
         this.collector = var3;
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
            Object var1 = this.collection;
            this.collection = null;
            this.complete((C)var1);
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.collection = null;
            this.downstream.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         if (!this.done) {
            try {
               this.collector.accept(this.collection, (T)var1);
            } catch (Throwable var3) {
               Exceptions.throwIfFatal(var3);
               this.cancel();
               this.onError(var3);
               return;
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
