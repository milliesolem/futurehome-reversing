package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Function;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableSubscriber;
import org.reactivestreams.Subscriber;

public final class FlowableDistinctUntilChanged<T, K> extends AbstractFlowableWithUpstream<T, T> {
   final BiPredicate<? super K, ? super K> comparer;
   final Function<? super T, K> keySelector;

   public FlowableDistinctUntilChanged(Flowable<T> var1, Function<? super T, K> var2, BiPredicate<? super K, ? super K> var3) {
      super(var1);
      this.keySelector = var2;
      this.comparer = var3;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      if (var1 instanceof ConditionalSubscriber) {
         ConditionalSubscriber var2 = (ConditionalSubscriber)var1;
         this.source.subscribe(new FlowableDistinctUntilChanged.DistinctUntilChangedConditionalSubscriber<>(var2, this.keySelector, this.comparer));
      } else {
         this.source.subscribe(new FlowableDistinctUntilChanged.DistinctUntilChangedSubscriber<>(var1, this.keySelector, this.comparer));
      }
   }

   static final class DistinctUntilChangedConditionalSubscriber<T, K> extends BasicFuseableConditionalSubscriber<T, T> {
      final BiPredicate<? super K, ? super K> comparer;
      boolean hasValue;
      final Function<? super T, K> keySelector;
      K last;

      DistinctUntilChangedConditionalSubscriber(ConditionalSubscriber<? super T> var1, Function<? super T, K> var2, BiPredicate<? super K, ? super K> var3) {
         super(var1);
         this.keySelector = var2;
         this.comparer = var3;
      }

      public void onNext(T var1) {
         if (!this.tryOnNext((T)var1)) {
            this.upstream.request(1L);
         }
      }

      @Override
      public T poll() throws Exception {
         while (true) {
            Object var2 = this.qs.poll();
            if (var2 == null) {
               return null;
            }

            Object var1 = this.keySelector.apply((T)var2);
            if (!this.hasValue) {
               this.hasValue = true;
               this.last = (K)var1;
               return (T)var2;
            }

            if (!this.comparer.test(this.last, (K)var1)) {
               this.last = (K)var1;
               return (T)var2;
            }

            this.last = (K)var1;
            if (this.sourceMode != 1) {
               this.upstream.request(1L);
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
            return this.downstream.tryOnNext((T)var1);
         } else {
            label43: {
               boolean var2;
               try {
                  Object var3 = this.keySelector.apply((T)var1);
                  if (!this.hasValue) {
                     this.hasValue = true;
                     this.last = (K)var3;
                     break label43;
                  }

                  var2 = this.comparer.test(this.last, (K)var3);
                  this.last = (K)var3;
               } catch (Throwable var5) {
                  this.fail(var5);
                  return true;
               }

               if (var2) {
                  return false;
               }
            }

            this.downstream.onNext(var1);
            return true;
         }
      }
   }

   static final class DistinctUntilChangedSubscriber<T, K> extends BasicFuseableSubscriber<T, T> implements ConditionalSubscriber<T> {
      final BiPredicate<? super K, ? super K> comparer;
      boolean hasValue;
      final Function<? super T, K> keySelector;
      K last;

      DistinctUntilChangedSubscriber(Subscriber<? super T> var1, Function<? super T, K> var2, BiPredicate<? super K, ? super K> var3) {
         super(var1);
         this.keySelector = var2;
         this.comparer = var3;
      }

      public void onNext(T var1) {
         if (!this.tryOnNext((T)var1)) {
            this.upstream.request(1L);
         }
      }

      @Override
      public T poll() throws Exception {
         while (true) {
            Object var1 = this.qs.poll();
            if (var1 == null) {
               return null;
            }

            Object var2 = this.keySelector.apply((T)var1);
            if (!this.hasValue) {
               this.hasValue = true;
               this.last = (K)var2;
               return (T)var1;
            }

            if (!this.comparer.test(this.last, (K)var2)) {
               this.last = (K)var2;
               return (T)var1;
            }

            this.last = (K)var2;
            if (this.sourceMode != 1) {
               this.upstream.request(1L);
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
            this.downstream.onNext(var1);
            return true;
         } else {
            label43: {
               boolean var2;
               try {
                  Object var3 = this.keySelector.apply((T)var1);
                  if (!this.hasValue) {
                     this.hasValue = true;
                     this.last = (K)var3;
                     break label43;
                  }

                  var2 = this.comparer.test(this.last, (K)var3);
                  this.last = (K)var3;
               } catch (Throwable var5) {
                  this.fail(var5);
                  return true;
               }

               if (var2) {
                  return false;
               }
            }

            this.downstream.onNext(var1);
            return true;
         }
      }
   }
}
