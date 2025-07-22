package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiPredicate;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;

public final class FlowableSequenceEqualSingle<T> extends Single<Boolean> implements FuseToFlowable<Boolean> {
   final BiPredicate<? super T, ? super T> comparer;
   final Publisher<? extends T> first;
   final int prefetch;
   final Publisher<? extends T> second;

   public FlowableSequenceEqualSingle(Publisher<? extends T> var1, Publisher<? extends T> var2, BiPredicate<? super T, ? super T> var3, int var4) {
      this.first = var1;
      this.second = var2;
      this.comparer = var3;
      this.prefetch = var4;
   }

   @Override
   public Flowable<Boolean> fuseToFlowable() {
      return RxJavaPlugins.onAssembly(new FlowableSequenceEqual<>(this.first, this.second, this.comparer, this.prefetch));
   }

   @Override
   public void subscribeActual(SingleObserver<? super Boolean> var1) {
      FlowableSequenceEqualSingle.EqualCoordinator var2 = new FlowableSequenceEqualSingle.EqualCoordinator<>(var1, this.prefetch, this.comparer);
      var1.onSubscribe(var2);
      var2.subscribe(this.first, this.second);
   }

   static final class EqualCoordinator<T> extends AtomicInteger implements Disposable, FlowableSequenceEqual.EqualCoordinatorHelper {
      private static final long serialVersionUID = -6178010334400373240L;
      final BiPredicate<? super T, ? super T> comparer;
      final SingleObserver<? super Boolean> downstream;
      final AtomicThrowable error;
      final FlowableSequenceEqual.EqualSubscriber<T> first;
      final FlowableSequenceEqual.EqualSubscriber<T> second;
      T v1;
      T v2;

      EqualCoordinator(SingleObserver<? super Boolean> var1, int var2, BiPredicate<? super T, ? super T> var3) {
         this.downstream = var1;
         this.comparer = var3;
         this.first = new FlowableSequenceEqual.EqualSubscriber<>(this, var2);
         this.second = new FlowableSequenceEqual.EqualSubscriber<>(this, var2);
         this.error = new AtomicThrowable();
      }

      void cancelAndClear() {
         this.first.cancel();
         this.first.clear();
         this.second.cancel();
         this.second.clear();
      }

      @Override
      public void dispose() {
         this.first.cancel();
         this.second.cancel();
         if (this.getAndIncrement() == 0) {
            this.first.clear();
            this.second.clear();
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void drain() {
         if (this.getAndIncrement() == 0) {
            int var1 = 1;

            int var23;
            do {
               SimpleQueue var10 = this.first.queue;
               SimpleQueue var9 = this.second.queue;
               if (var10 != null && var9 != null) {
                  while (true) {
                     if (this.isDisposed()) {
                        this.first.clear();
                        this.second.clear();
                        return;
                     }

                     if (this.error.get() != null) {
                        this.cancelAndClear();
                        this.downstream.onError(this.error.terminate());
                        return;
                     }

                     boolean var4 = this.first.done;
                     Object var7 = this.v1;
                     Object var6 = var7;
                     if (var7 == null) {
                        try {
                           var6 = var10.poll();
                        } catch (Throwable var22) {
                           Exceptions.throwIfFatal(var22);
                           this.cancelAndClear();
                           this.error.addThrowable(var22);
                           this.downstream.onError(this.error.terminate());
                           return;
                        }

                        this.v1 = (T)var6;
                     }

                     boolean var2;
                     if (var6 == null) {
                        var2 = true;
                     } else {
                        var2 = false;
                     }

                     boolean var5 = this.second.done;
                     Object var8 = this.v2;
                     var7 = var8;
                     if (var8 == null) {
                        try {
                           var7 = var9.poll();
                        } catch (Throwable var21) {
                           Exceptions.throwIfFatal(var21);
                           this.cancelAndClear();
                           this.error.addThrowable(var21);
                           this.downstream.onError(this.error.terminate());
                           return;
                        }

                        this.v2 = (T)var7;
                     }

                     boolean var3;
                     if (var7 == null) {
                        var3 = true;
                     } else {
                        var3 = false;
                     }

                     if (var4 && var5 && var2 && var3) {
                        this.downstream.onSuccess(true);
                        return;
                     }

                     if (var4 && var5 && var2 != var3) {
                        this.cancelAndClear();
                        this.downstream.onSuccess(false);
                        return;
                     }

                     if (var2 || var3) {
                        break;
                     }

                     try {
                        var4 = this.comparer.test((T)var6, (T)var7);
                     } catch (Throwable var20) {
                        Exceptions.throwIfFatal(var20);
                        this.cancelAndClear();
                        this.error.addThrowable(var20);
                        this.downstream.onError(this.error.terminate());
                        return;
                     }

                     if (!var4) {
                        this.cancelAndClear();
                        this.downstream.onSuccess(false);
                        return;
                     }

                     this.v1 = null;
                     this.v2 = null;
                     this.first.request();
                     this.second.request();
                  }
               } else {
                  if (this.isDisposed()) {
                     this.first.clear();
                     this.second.clear();
                     return;
                  }

                  if (this.error.get() != null) {
                     this.cancelAndClear();
                     this.downstream.onError(this.error.terminate());
                     return;
                  }
               }

               var23 = this.addAndGet(-var1);
               var1 = var23;
            } while (var23 != 0);
         }
      }

      @Override
      public void innerError(Throwable var1) {
         if (this.error.addThrowable(var1)) {
            this.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.first.get() == SubscriptionHelper.CANCELLED) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      void subscribe(Publisher<? extends T> var1, Publisher<? extends T> var2) {
         var1.subscribe(this.first);
         var2.subscribe(this.second);
      }
   }
}
