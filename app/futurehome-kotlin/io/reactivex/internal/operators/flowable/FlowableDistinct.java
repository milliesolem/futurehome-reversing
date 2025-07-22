package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscribers.BasicFuseableSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscriber;

public final class FlowableDistinct<T, K> extends AbstractFlowableWithUpstream<T, T> {
   final Callable<? extends Collection<? super K>> collectionSupplier;
   final Function<? super T, K> keySelector;

   public FlowableDistinct(Flowable<T> var1, Function<? super T, K> var2, Callable<? extends Collection<? super K>> var3) {
      super(var1);
      this.keySelector = var2;
      this.collectionSupplier = var3;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      Collection var2;
      try {
         var2 = ObjectHelper.requireNonNull(
            this.collectionSupplier.call(),
            "The collectionSupplier returned a null collection. Null values are generally not allowed in 2.x operators and sources."
         );
      } catch (Throwable var4) {
         Exceptions.throwIfFatal(var4);
         EmptySubscription.error(var4, var1);
         return;
      }

      this.source.subscribe(new FlowableDistinct.DistinctSubscriber<>(var1, this.keySelector, var2));
   }

   static final class DistinctSubscriber<T, K> extends BasicFuseableSubscriber<T, T> {
      final Collection<? super K> collection;
      final Function<? super T, K> keySelector;

      DistinctSubscriber(Subscriber<? super T> var1, Function<? super T, K> var2, Collection<? super K> var3) {
         super(var1);
         this.keySelector = var2;
         this.collection = var3;
      }

      @Override
      public void clear() {
         this.collection.clear();
         super.clear();
      }

      @Override
      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.collection.clear();
            this.downstream.onComplete();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.collection.clear();
            this.downstream.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         if (!this.done) {
            if (this.sourceMode == 0) {
               boolean var2;
               try {
                  Object var3 = ObjectHelper.requireNonNull(this.keySelector.apply((T)var1), "The keySelector returned a null key");
                  var2 = this.collection.add((K)var3);
               } catch (Throwable var5) {
                  this.fail(var5);
                  return;
               }

               if (var2) {
                  this.downstream.onNext(var1);
               } else {
                  this.upstream.request(1L);
               }
            } else {
               this.downstream.onNext(null);
            }
         }
      }

      @Override
      public T poll() throws Exception {
         while (true) {
            Object var1 = this.qs.poll();
            if (var1 == null || this.collection.add(ObjectHelper.requireNonNull(this.keySelector.apply((T)var1), "The keySelector returned a null key"))) {
               return (T)var1;
            }

            if (this.sourceMode == 2) {
               this.upstream.request(1L);
            }
         }
      }

      @Override
      public int requestFusion(int var1) {
         return this.transitiveBoundaryFusion(var1);
      }
   }
}
