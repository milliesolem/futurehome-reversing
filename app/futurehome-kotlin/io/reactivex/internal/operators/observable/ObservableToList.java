package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.Collection;
import java.util.concurrent.Callable;

public final class ObservableToList<T, U extends Collection<? super T>> extends AbstractObservableWithUpstream<T, U> {
   final Callable<U> collectionSupplier;

   public ObservableToList(ObservableSource<T> var1, int var2) {
      super(var1);
      this.collectionSupplier = Functions.createArrayList(var2);
   }

   public ObservableToList(ObservableSource<T> var1, Callable<U> var2) {
      super(var1);
      this.collectionSupplier = var2;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void subscribeActual(Observer<? super U> var1) {
      Collection var2;
      try {
         var2 = ObjectHelper.requireNonNull(
            this.collectionSupplier.call(),
            "The collectionSupplier returned a null collection. Null values are generally not allowed in 2.x operators and sources."
         );
      } catch (Throwable var4) {
         Exceptions.throwIfFatal(var4);
         EmptyDisposable.error(var4, var1);
         return;
      }

      this.source.subscribe(new ObservableToList.ToListObserver<>(var1, (U)var2));
   }

   static final class ToListObserver<T, U extends Collection<? super T>> implements Observer<T>, Disposable {
      U collection;
      final Observer<? super U> downstream;
      Disposable upstream;

      ToListObserver(Observer<? super U> var1, U var2) {
         this.downstream = var1;
         this.collection = (U)var2;
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
      }

      @Override
      public void onComplete() {
         Collection var1 = this.collection;
         this.collection = null;
         this.downstream.onNext((U)var1);
         this.downstream.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         this.collection = null;
         this.downstream.onError(var1);
      }

      @Override
      public void onNext(T var1) {
         this.collection.add((T)var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }
   }
}
