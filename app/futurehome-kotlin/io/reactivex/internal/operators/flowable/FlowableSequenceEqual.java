package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.BiPredicate;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSequenceEqual<T> extends Flowable<Boolean> {
   final BiPredicate<? super T, ? super T> comparer;
   final Publisher<? extends T> first;
   final int prefetch;
   final Publisher<? extends T> second;

   public FlowableSequenceEqual(Publisher<? extends T> var1, Publisher<? extends T> var2, BiPredicate<? super T, ? super T> var3, int var4) {
      this.first = var1;
      this.second = var2;
      this.comparer = var3;
      this.prefetch = var4;
   }

   @Override
   public void subscribeActual(Subscriber<? super Boolean> var1) {
      FlowableSequenceEqual.EqualCoordinator var2 = new FlowableSequenceEqual.EqualCoordinator<>(var1, this.prefetch, this.comparer);
      var1.onSubscribe(var2);
      var2.subscribe(this.first, this.second);
   }

   static final class EqualCoordinator<T> extends DeferredScalarSubscription<Boolean> implements FlowableSequenceEqual.EqualCoordinatorHelper {
      private static final long serialVersionUID = -6178010334400373240L;
      final BiPredicate<? super T, ? super T> comparer;
      final AtomicThrowable error;
      final FlowableSequenceEqual.EqualSubscriber<T> first;
      final FlowableSequenceEqual.EqualSubscriber<T> second;
      T v1;
      T v2;
      final AtomicInteger wip;

      EqualCoordinator(Subscriber<? super Boolean> var1, int var2, BiPredicate<? super T, ? super T> var3) {
         super(var1);
         this.comparer = var3;
         this.wip = new AtomicInteger();
         this.first = new FlowableSequenceEqual.EqualSubscriber<>(this, var2);
         this.second = new FlowableSequenceEqual.EqualSubscriber<>(this, var2);
         this.error = new AtomicThrowable();
      }

      @Override
      public void cancel() {
         super.cancel();
         this.first.cancel();
         this.second.cancel();
         if (this.wip.getAndIncrement() == 0) {
            this.first.clear();
            this.second.clear();
         }
      }

      void cancelAndClear() {
         this.first.cancel();
         this.first.clear();
         this.second.cancel();
         this.second.clear();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void drain() {
         if (this.wip.getAndIncrement() == 0) {
            int var1 = 1;

            int var23;
            do {
               SimpleQueue var9 = this.first.queue;
               SimpleQueue var10 = this.second.queue;
               if (var9 != null && var10 != null) {
                  while (true) {
                     if (this.isCancelled()) {
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
                           var6 = var9.poll();
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
                           var7 = var10.poll();
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
                        this.complete(true);
                        return;
                     }

                     if (var4 && var5 && var2 != var3) {
                        this.cancelAndClear();
                        this.complete(false);
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
                        this.complete(false);
                        return;
                     }

                     this.v1 = null;
                     this.v2 = null;
                     this.first.request();
                     this.second.request();
                  }
               } else {
                  if (this.isCancelled()) {
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

               var23 = this.wip.addAndGet(-var1);
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

      void subscribe(Publisher<? extends T> var1, Publisher<? extends T> var2) {
         var1.subscribe(this.first);
         var2.subscribe(this.second);
      }
   }

   interface EqualCoordinatorHelper {
      void drain();

      void innerError(Throwable var1);
   }

   static final class EqualSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T> {
      private static final long serialVersionUID = 4804128302091633067L;
      volatile boolean done;
      final int limit;
      final FlowableSequenceEqual.EqualCoordinatorHelper parent;
      final int prefetch;
      long produced;
      volatile SimpleQueue<T> queue;
      int sourceMode;

      EqualSubscriber(FlowableSequenceEqual.EqualCoordinatorHelper var1, int var2) {
         this.parent = var1;
         this.limit = var2 - (var2 >> 2);
         this.prefetch = var2;
      }

      public void cancel() {
         SubscriptionHelper.cancel(this);
      }

      void clear() {
         SimpleQueue var1 = this.queue;
         if (var1 != null) {
            var1.clear();
         }
      }

      public void onComplete() {
         this.done = true;
         this.parent.drain();
      }

      public void onError(Throwable var1) {
         this.parent.innerError(var1);
      }

      public void onNext(T var1) {
         if (this.sourceMode == 0 && !this.queue.offer((T)var1)) {
            this.onError(new MissingBackpressureException());
         } else {
            this.parent.drain();
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.setOnce(this, var1)) {
            if (var1 instanceof QueueSubscription) {
               QueueSubscription var3 = (QueueSubscription)var1;
               int var2 = var3.requestFusion(3);
               if (var2 == 1) {
                  this.sourceMode = var2;
                  this.queue = var3;
                  this.done = true;
                  this.parent.drain();
                  return;
               }

               if (var2 == 2) {
                  this.sourceMode = var2;
                  this.queue = var3;
                  var1.request(this.prefetch);
                  return;
               }
            }

            this.queue = new SpscArrayQueue<>(this.prefetch);
            var1.request(this.prefetch);
         }
      }

      public void request() {
         if (this.sourceMode != 1) {
            long var1 = this.produced + 1L;
            if (var1 >= this.limit) {
               this.produced = 0L;
               this.get().request(var1);
            } else {
               this.produced = var1;
            }
         }
      }
   }
}
