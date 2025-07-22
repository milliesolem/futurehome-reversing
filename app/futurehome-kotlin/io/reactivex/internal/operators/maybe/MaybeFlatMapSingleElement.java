package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeFlatMapSingleElement<T, R> extends Maybe<R> {
   final Function<? super T, ? extends SingleSource<? extends R>> mapper;
   final MaybeSource<T> source;

   public MaybeFlatMapSingleElement(MaybeSource<T> var1, Function<? super T, ? extends SingleSource<? extends R>> var2) {
      this.source = var1;
      this.mapper = var2;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super R> var1) {
      this.source.subscribe(new MaybeFlatMapSingleElement.FlatMapMaybeObserver<>(var1, this.mapper));
   }

   static final class FlatMapMaybeObserver<T, R> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable {
      private static final long serialVersionUID = 4827726964688405508L;
      final MaybeObserver<? super R> downstream;
      final Function<? super T, ? extends SingleSource<? extends R>> mapper;

      FlatMapMaybeObserver(MaybeObserver<? super R> var1, Function<? super T, ? extends SingleSource<? extends R>> var2) {
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
         if (DisposableHelper.setOnce(this, var1)) {
            this.downstream.onSubscribe(this);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onSuccess(T var1) {
         try {
            var1 = ObjectHelper.requireNonNull(this.mapper.apply((T)var1), "The mapper returned a null SingleSource");
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            this.onError(var3);
            return;
         }

         var1.subscribe(new MaybeFlatMapSingleElement.FlatMapSingleObserver<>(this, this.downstream));
      }
   }

   static final class FlatMapSingleObserver<R> implements SingleObserver<R> {
      final MaybeObserver<? super R> downstream;
      final AtomicReference<Disposable> parent;

      FlatMapSingleObserver(AtomicReference<Disposable> var1, MaybeObserver<? super R> var2) {
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
