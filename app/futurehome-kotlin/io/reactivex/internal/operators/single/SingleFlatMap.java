package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleFlatMap<T, R> extends Single<R> {
   final Function<? super T, ? extends SingleSource<? extends R>> mapper;
   final SingleSource<? extends T> source;

   public SingleFlatMap(SingleSource<? extends T> var1, Function<? super T, ? extends SingleSource<? extends R>> var2) {
      this.mapper = var2;
      this.source = var1;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super R> var1) {
      this.source.subscribe(new SingleFlatMap.SingleFlatMapCallback<>(var1, this.mapper));
   }

   static final class SingleFlatMapCallback<T, R> extends AtomicReference<Disposable> implements SingleObserver<T>, Disposable {
      private static final long serialVersionUID = 3258103020495908596L;
      final SingleObserver<? super R> downstream;
      final Function<? super T, ? extends SingleSource<? extends R>> mapper;

      SingleFlatMapCallback(SingleObserver<? super R> var1, Function<? super T, ? extends SingleSource<? extends R>> var2) {
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
      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.setOnce(this, var1)) {
            this.downstream.onSubscribe(this);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onSuccess(T var1) {
         try {
            var1 = ObjectHelper.requireNonNull(this.mapper.apply((T)var1), "The single returned by the mapper is null");
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            this.downstream.onError(var3);
            return;
         }

         if (!this.isDisposed()) {
            var1.subscribe(new SingleFlatMap.SingleFlatMapCallback.FlatMapSingleObserver<>(this, this.downstream));
         }
      }

      static final class FlatMapSingleObserver<R> implements SingleObserver<R> {
         final SingleObserver<? super R> downstream;
         final AtomicReference<Disposable> parent;

         FlatMapSingleObserver(AtomicReference<Disposable> var1, SingleObserver<? super R> var2) {
            this.parent = var1;
            this.downstream = var2;
         }

         @Override
         public void onError(Throwable var1) {
            this.downstream.onError(var1);
         }

         @Override
         public void onSubscribe(Disposable var1) {
            DisposableHelper.replace(this.parent, var1);
         }

         @Override
         public void onSuccess(R var1) {
            this.downstream.onSuccess((R)var1);
         }
      }
   }
}
