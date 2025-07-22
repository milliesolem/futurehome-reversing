package io.reactivex.internal.operators.mixed;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleFlatMapObservable<T, R> extends Observable<R> {
   final Function<? super T, ? extends ObservableSource<? extends R>> mapper;
   final SingleSource<T> source;

   public SingleFlatMapObservable(SingleSource<T> var1, Function<? super T, ? extends ObservableSource<? extends R>> var2) {
      this.source = var1;
      this.mapper = var2;
   }

   @Override
   protected void subscribeActual(Observer<? super R> var1) {
      SingleFlatMapObservable.FlatMapObserver var2 = new SingleFlatMapObservable.FlatMapObserver<>(var1, this.mapper);
      var1.onSubscribe(var2);
      this.source.subscribe(var2);
   }

   static final class FlatMapObserver<T, R> extends AtomicReference<Disposable> implements Observer<R>, SingleObserver<T>, Disposable {
      private static final long serialVersionUID = -8948264376121066672L;
      final Observer<? super R> downstream;
      final Function<? super T, ? extends ObservableSource<? extends R>> mapper;

      FlatMapObserver(Observer<? super R> var1, Function<? super T, ? extends ObservableSource<? extends R>> var2) {
         this.downstream = var1;
         this.mapper = var2;
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this);
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.get());
      }

      @Override
      public void onComplete() {
         this.downstream.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      @Override
      public void onNext(R var1) {
         this.downstream.onNext((R)var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.replace(this, var1);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onSuccess(T var1) {
         try {
            var1 = ObjectHelper.requireNonNull(this.mapper.apply((T)var1), "The mapper returned a null Publisher");
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            this.downstream.onError(var3);
            return;
         }

         var1.subscribe(this);
      }
   }
}
