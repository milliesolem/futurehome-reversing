package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableSubscriber;
import org.reactivestreams.Subscriber;

public final class FlowableFilter<T> extends AbstractFlowableWithUpstream<T, T> {
   final Predicate<? super T> predicate;

   public FlowableFilter(Flowable<T> var1, Predicate<? super T> var2) {
      super(var1);
      this.predicate = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      if (var1 instanceof ConditionalSubscriber) {
         this.source.subscribe(new FlowableFilter.FilterConditionalSubscriber<>((ConditionalSubscriber<? super T>)var1, this.predicate));
      } else {
         this.source.subscribe(new FlowableFilter.FilterSubscriber<>(var1, this.predicate));
      }
   }

   static final class FilterConditionalSubscriber<T> extends BasicFuseableConditionalSubscriber<T, T> {
      final Predicate<? super T> filter;

      FilterConditionalSubscriber(ConditionalSubscriber<? super T> var1, Predicate<? super T> var2) {
         super(var1);
         this.filter = var2;
      }

      public void onNext(T var1) {
         if (!this.tryOnNext((T)var1)) {
            this.upstream.request(1L);
         }
      }

      @Override
      public T poll() throws Exception {
         QueueSubscription var3 = this.qs;
         Predicate var1 = this.filter;

         while (true) {
            Object var2 = var3.poll();
            if (var2 == null) {
               return null;
            }

            if (var1.test(var2)) {
               return (T)var2;
            }

            if (this.sourceMode == 2) {
               var3.request(1L);
            }
         }
      }

      @Override
      public int requestFusion(int var1) {
         return this.transitiveBoundaryFusion(var1);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public boolean tryOnNext(T var1) {
         boolean var2 = this.done;
         boolean var3 = false;
         if (var2) {
            return false;
         } else if (this.sourceMode != 0) {
            return this.downstream.tryOnNext(null);
         } else {
            boolean var4;
            try {
               var4 = this.filter.test((T)var1);
            } catch (Throwable var6) {
               this.fail(var6);
               return true;
            }

            var2 = var3;
            if (var4) {
               var2 = var3;
               if (this.downstream.tryOnNext((T)var1)) {
                  var2 = true;
               }
            }

            return var2;
         }
      }
   }

   static final class FilterSubscriber<T> extends BasicFuseableSubscriber<T, T> implements ConditionalSubscriber<T> {
      final Predicate<? super T> filter;

      FilterSubscriber(Subscriber<? super T> var1, Predicate<? super T> var2) {
         super(var1);
         this.filter = var2;
      }

      public void onNext(T var1) {
         if (!this.tryOnNext((T)var1)) {
            this.upstream.request(1L);
         }
      }

      @Override
      public T poll() throws Exception {
         QueueSubscription var1 = this.qs;
         Predicate var2 = this.filter;

         while (true) {
            Object var3 = var1.poll();
            if (var3 == null) {
               return null;
            }

            if (var2.test(var3)) {
               return (T)var3;
            }

            if (this.sourceMode == 2) {
               var1.request(1L);
            }
         }
      }

      @Override
      public int requestFusion(int var1) {
         return this.transitiveBoundaryFusion(var1);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public boolean tryOnNext(T var1) {
         if (this.done) {
            return false;
         } else if (this.sourceMode != 0) {
            this.downstream.onNext(null);
            return true;
         } else {
            boolean var2;
            try {
               var2 = this.filter.test((T)var1);
            } catch (Throwable var4) {
               this.fail(var4);
               return true;
            }

            if (var2) {
               this.downstream.onNext(var1);
            }

            return var2;
         }
      }
   }
}
