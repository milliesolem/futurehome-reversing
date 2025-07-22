package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeFlatMapBiSelector<T, U, R> extends AbstractMaybeWithUpstream<T, R> {
   final Function<? super T, ? extends MaybeSource<? extends U>> mapper;
   final BiFunction<? super T, ? super U, ? extends R> resultSelector;

   public MaybeFlatMapBiSelector(
      MaybeSource<T> var1, Function<? super T, ? extends MaybeSource<? extends U>> var2, BiFunction<? super T, ? super U, ? extends R> var3
   ) {
      super(var1);
      this.mapper = var2;
      this.resultSelector = var3;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super R> var1) {
      this.source.subscribe(new MaybeFlatMapBiSelector.FlatMapBiMainObserver<>(var1, this.mapper, this.resultSelector));
   }

   static final class FlatMapBiMainObserver<T, U, R> implements MaybeObserver<T>, Disposable {
      final MaybeFlatMapBiSelector.FlatMapBiMainObserver.InnerObserver<T, U, R> inner;
      final Function<? super T, ? extends MaybeSource<? extends U>> mapper;

      FlatMapBiMainObserver(
         MaybeObserver<? super R> var1, Function<? super T, ? extends MaybeSource<? extends U>> var2, BiFunction<? super T, ? super U, ? extends R> var3
      ) {
         this.inner = new MaybeFlatMapBiSelector.FlatMapBiMainObserver.InnerObserver<>(var1, var3);
         this.mapper = var2;
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this.inner);
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.inner.get());
      }

      @Override
      public void onComplete() {
         this.inner.downstream.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         this.inner.downstream.onError(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.setOnce(this.inner, var1)) {
            this.inner.downstream.onSubscribe(this);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onSuccess(T var1) {
         MaybeSource var2;
         try {
            var2 = ObjectHelper.requireNonNull(this.mapper.apply((T)var1), "The mapper returned a null MaybeSource");
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            this.inner.downstream.onError(var4);
            return;
         }

         if (DisposableHelper.replace(this.inner, null)) {
            this.inner.value = (T)var1;
            var2.subscribe(this.inner);
         }
      }

      static final class InnerObserver<T, U, R> extends AtomicReference<Disposable> implements MaybeObserver<U> {
         private static final long serialVersionUID = -2897979525538174559L;
         final MaybeObserver<? super R> downstream;
         final BiFunction<? super T, ? super U, ? extends R> resultSelector;
         T value;

         InnerObserver(MaybeObserver<? super R> var1, BiFunction<? super T, ? super U, ? extends R> var2) {
            this.downstream = var1;
            this.resultSelector = var2;
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
            DisposableHelper.setOnce(this, var1);
         }

         // $VF: Could not inline inconsistent finally blocks
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         @Override
         public void onSuccess(U var1) {
            Object var2 = this.value;
            this.value = null;

            try {
               var1 = ObjectHelper.requireNonNull(this.resultSelector.apply((T)var2, (U)var1), "The resultSelector returned a null value");
            } catch (Throwable var4) {
               Exceptions.throwIfFatal(var4);
               this.downstream.onError(var4);
               return;
            }

            this.downstream.onSuccess((R)var1);
         }
      }
   }
}
