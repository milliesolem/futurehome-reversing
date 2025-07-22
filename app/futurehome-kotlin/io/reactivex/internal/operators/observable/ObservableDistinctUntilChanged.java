package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Function;
import io.reactivex.internal.observers.BasicFuseableObserver;

public final class ObservableDistinctUntilChanged<T, K> extends AbstractObservableWithUpstream<T, T> {
   final BiPredicate<? super K, ? super K> comparer;
   final Function<? super T, K> keySelector;

   public ObservableDistinctUntilChanged(ObservableSource<T> var1, Function<? super T, K> var2, BiPredicate<? super K, ? super K> var3) {
      super(var1);
      this.keySelector = var2;
      this.comparer = var3;
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      this.source.subscribe(new ObservableDistinctUntilChanged.DistinctUntilChangedObserver<>(var1, this.keySelector, this.comparer));
   }

   static final class DistinctUntilChangedObserver<T, K> extends BasicFuseableObserver<T, T> {
      final BiPredicate<? super K, ? super K> comparer;
      boolean hasValue;
      final Function<? super T, K> keySelector;
      K last;

      DistinctUntilChangedObserver(Observer<? super T> var1, Function<? super T, K> var2, BiPredicate<? super K, ? super K> var3) {
         super(var1);
         this.keySelector = var2;
         this.comparer = var3;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onNext(T var1) {
         if (!this.done) {
            if (this.sourceMode != 0) {
               this.downstream.onNext((T)var1);
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
                     return;
                  }

                  if (var2) {
                     return;
                  }
               }

               this.downstream.onNext((T)var1);
            }
         }
      }

      @Override
      public T poll() throws Exception {
         while (true) {
            Object var2 = this.qd.poll();
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
         }
      }

      @Override
      public int requestFusion(int var1) {
         return this.transitiveBoundaryFusion(var1);
      }
   }
}
