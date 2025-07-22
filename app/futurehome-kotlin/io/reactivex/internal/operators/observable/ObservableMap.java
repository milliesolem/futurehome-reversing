package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.BasicFuseableObserver;

public final class ObservableMap<T, U> extends AbstractObservableWithUpstream<T, U> {
   final Function<? super T, ? extends U> function;

   public ObservableMap(ObservableSource<T> var1, Function<? super T, ? extends U> var2) {
      super(var1);
      this.function = var2;
   }

   @Override
   public void subscribeActual(Observer<? super U> var1) {
      this.source.subscribe(new ObservableMap.MapObserver<>(var1, this.function));
   }

   static final class MapObserver<T, U> extends BasicFuseableObserver<T, U> {
      final Function<? super T, ? extends U> mapper;

      MapObserver(Observer<? super U> var1, Function<? super T, ? extends U> var2) {
         super(var1);
         this.mapper = var2;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onNext(T var1) {
         if (!this.done) {
            if (this.sourceMode != 0) {
               this.downstream.onNext(null);
            } else {
               try {
                  var1 = ObjectHelper.requireNonNull(this.mapper.apply((T)var1), "The mapper function returned a null value.");
               } catch (Throwable var3) {
                  this.fail(var3);
                  return;
               }

               this.downstream.onNext((U)var1);
            }
         }
      }

      @Override
      public U poll() throws Exception {
         Object var1 = this.qd.poll();
         if (var1 != null) {
            var1 = ObjectHelper.requireNonNull(this.mapper.apply((T)var1), "The mapper function returned a null value.");
         } else {
            var1 = null;
         }

         return (U)var1;
      }

      @Override
      public int requestFusion(int var1) {
         return this.transitiveBoundaryFusion(var1);
      }
   }
}
