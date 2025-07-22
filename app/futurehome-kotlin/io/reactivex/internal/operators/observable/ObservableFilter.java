package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.observers.BasicFuseableObserver;

public final class ObservableFilter<T> extends AbstractObservableWithUpstream<T, T> {
   final Predicate<? super T> predicate;

   public ObservableFilter(ObservableSource<T> var1, Predicate<? super T> var2) {
      super(var1);
      this.predicate = var2;
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      this.source.subscribe(new ObservableFilter.FilterObserver<>(var1, this.predicate));
   }

   static final class FilterObserver<T> extends BasicFuseableObserver<T, T> {
      final Predicate<? super T> filter;

      FilterObserver(Observer<? super T> var1, Predicate<? super T> var2) {
         super(var1);
         this.filter = var2;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onNext(T var1) {
         if (this.sourceMode == 0) {
            boolean var2;
            try {
               var2 = this.filter.test((T)var1);
            } catch (Throwable var4) {
               this.fail(var4);
               return;
            }

            if (var2) {
               this.downstream.onNext((T)var1);
            }
         } else {
            this.downstream.onNext(null);
         }
      }

      @Override
      public T poll() throws Exception {
         Object var1;
         do {
            var1 = this.qd.poll();
         } while (var1 != null && !this.filter.test((T)var1));

         return (T)var1;
      }

      @Override
      public int requestFusion(int var1) {
         return this.transitiveBoundaryFusion(var1);
      }
   }
}
