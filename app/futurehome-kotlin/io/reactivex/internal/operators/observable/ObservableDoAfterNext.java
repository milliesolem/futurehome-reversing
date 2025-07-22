package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.observers.BasicFuseableObserver;

public final class ObservableDoAfterNext<T> extends AbstractObservableWithUpstream<T, T> {
   final Consumer<? super T> onAfterNext;

   public ObservableDoAfterNext(ObservableSource<T> var1, Consumer<? super T> var2) {
      super(var1);
      this.onAfterNext = var2;
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      this.source.subscribe(new ObservableDoAfterNext.DoAfterObserver<>(var1, this.onAfterNext));
   }

   static final class DoAfterObserver<T> extends BasicFuseableObserver<T, T> {
      final Consumer<? super T> onAfterNext;

      DoAfterObserver(Observer<? super T> var1, Consumer<? super T> var2) {
         super(var1);
         this.onAfterNext = var2;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onNext(T var1) {
         this.downstream.onNext((T)var1);
         if (this.sourceMode == 0) {
            try {
               this.onAfterNext.accept((T)var1);
            } catch (Throwable var3) {
               this.fail(var3);
               return;
            }
         }
      }

      @Override
      public T poll() throws Exception {
         Object var1 = this.qd.poll();
         if (var1 != null) {
            this.onAfterNext.accept((T)var1);
         }

         return (T)var1;
      }

      @Override
      public int requestFusion(int var1) {
         return this.transitiveBoundaryFusion(var1);
      }
   }
}
