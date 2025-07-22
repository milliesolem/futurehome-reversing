package io.reactivex.internal.operators.single;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleFlatMapCompletable<T> extends Completable {
   final Function<? super T, ? extends CompletableSource> mapper;
   final SingleSource<T> source;

   public SingleFlatMapCompletable(SingleSource<T> var1, Function<? super T, ? extends CompletableSource> var2) {
      this.source = var1;
      this.mapper = var2;
   }

   @Override
   protected void subscribeActual(CompletableObserver var1) {
      SingleFlatMapCompletable.FlatMapCompletableObserver var2 = new SingleFlatMapCompletable.FlatMapCompletableObserver<>(var1, this.mapper);
      var1.onSubscribe(var2);
      this.source.subscribe(var2);
   }

   static final class FlatMapCompletableObserver<T> extends AtomicReference<Disposable> implements SingleObserver<T>, CompletableObserver, Disposable {
      private static final long serialVersionUID = -2177128922851101253L;
      final CompletableObserver downstream;
      final Function<? super T, ? extends CompletableSource> mapper;

      FlatMapCompletableObserver(CompletableObserver var1, Function<? super T, ? extends CompletableSource> var2) {
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
      public void onSubscribe(Disposable var1) {
         DisposableHelper.replace(this, var1);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onSuccess(T var1) {
         try {
            var1 = ObjectHelper.requireNonNull(this.mapper.apply((T)var1), "The mapper returned a null CompletableSource");
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            this.onError(var3);
            return;
         }

         if (!this.isDisposed()) {
            var1.subscribe(this);
         }
      }
   }
}
