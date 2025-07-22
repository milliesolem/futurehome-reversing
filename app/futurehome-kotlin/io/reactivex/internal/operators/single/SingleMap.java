package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;

public final class SingleMap<T, R> extends Single<R> {
   final Function<? super T, ? extends R> mapper;
   final SingleSource<? extends T> source;

   public SingleMap(SingleSource<? extends T> var1, Function<? super T, ? extends R> var2) {
      this.source = var1;
      this.mapper = var2;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super R> var1) {
      this.source.subscribe(new SingleMap.MapSingleObserver<>(var1, this.mapper));
   }

   static final class MapSingleObserver<T, R> implements SingleObserver<T> {
      final Function<? super T, ? extends R> mapper;
      final SingleObserver<? super R> t;

      MapSingleObserver(SingleObserver<? super R> var1, Function<? super T, ? extends R> var2) {
         this.t = var1;
         this.mapper = var2;
      }

      @Override
      public void onError(Throwable var1) {
         this.t.onError(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         this.t.onSubscribe(var1);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onSuccess(T var1) {
         try {
            var1 = ObjectHelper.requireNonNull(this.mapper.apply((T)var1), "The mapper function returned a null value.");
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            this.onError(var3);
            return;
         }

         this.t.onSuccess((R)var1);
      }
   }
}
