package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeFlatten<T, R> extends AbstractMaybeWithUpstream<T, R> {
   final Function<? super T, ? extends MaybeSource<? extends R>> mapper;

   public MaybeFlatten(MaybeSource<T> var1, Function<? super T, ? extends MaybeSource<? extends R>> var2) {
      super(var1);
      this.mapper = var2;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super R> var1) {
      this.source.subscribe(new MaybeFlatten.FlatMapMaybeObserver<>(var1, this.mapper));
   }

   static final class FlatMapMaybeObserver<T, R> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable {
      private static final long serialVersionUID = 4375739915521278546L;
      final MaybeObserver<? super R> downstream;
      final Function<? super T, ? extends MaybeSource<? extends R>> mapper;
      Disposable upstream;

      FlatMapMaybeObserver(MaybeObserver<? super R> var1, Function<? super T, ? extends MaybeSource<? extends R>> var2) {
         this.downstream = var1;
         this.mapper = var2;
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this);
         this.upstream.dispose();
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
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      @Override
      public void onSuccess(T var1) {
         try {
            var1 = ObjectHelper.requireNonNull(this.mapper.apply((T)var1), "The mapper returned a null MaybeSource");
         } catch (Exception var2) {
            Exceptions.throwIfFatal(var2);
            this.downstream.onError(var2);
            return;
         }

         if (!this.isDisposed()) {
            var1.subscribe(new MaybeFlatten.FlatMapMaybeObserver.InnerObserver(this));
         }
      }

      final class InnerObserver implements MaybeObserver<R> {
         final MaybeFlatten.FlatMapMaybeObserver this$0;

         InnerObserver(MaybeFlatten.FlatMapMaybeObserver var1) {
            this.this$0 = var1;
         }

         @Override
         public void onComplete() {
            this.this$0.downstream.onComplete();
         }

         @Override
         public void onError(Throwable var1) {
            this.this$0.downstream.onError(var1);
         }

         @Override
         public void onSubscribe(Disposable var1) {
            DisposableHelper.setOnce(this.this$0, var1);
         }

         @Override
         public void onSuccess(R var1) {
            this.this$0.downstream.onSuccess((R)var1);
         }
      }
   }
}
