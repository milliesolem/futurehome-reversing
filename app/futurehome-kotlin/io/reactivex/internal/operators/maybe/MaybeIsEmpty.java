package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

public final class MaybeIsEmpty<T> extends AbstractMaybeWithUpstream<T, Boolean> {
   public MaybeIsEmpty(MaybeSource<T> var1) {
      super(var1);
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super Boolean> var1) {
      this.source.subscribe(new MaybeIsEmpty.IsEmptyMaybeObserver<>(var1));
   }

   static final class IsEmptyMaybeObserver<T> implements MaybeObserver<T>, Disposable {
      final MaybeObserver<? super Boolean> downstream;
      Disposable upstream;

      IsEmptyMaybeObserver(MaybeObserver<? super Boolean> var1) {
         this.downstream = var1;
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
         this.downstream.onSuccess(true);
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
         this.downstream.onSuccess(false);
      }
   }
}
