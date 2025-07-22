package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.HasUpstreamMaybeSource;

public final class MaybeContains<T> extends Single<Boolean> implements HasUpstreamMaybeSource<T> {
   final MaybeSource<T> source;
   final Object value;

   public MaybeContains(MaybeSource<T> var1, Object var2) {
      this.source = var1;
      this.value = var2;
   }

   @Override
   public MaybeSource<T> source() {
      return this.source;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super Boolean> var1) {
      this.source.subscribe(new MaybeContains.ContainsMaybeObserver(var1, this.value));
   }

   static final class ContainsMaybeObserver implements MaybeObserver<Object>, Disposable {
      final SingleObserver<? super Boolean> downstream;
      Disposable upstream;
      final Object value;

      ContainsMaybeObserver(SingleObserver<? super Boolean> var1, Object var2) {
         this.downstream = var1;
         this.value = var2;
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
         this.upstream = DisposableHelper.DISPOSED;
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
      }

      @Override
      public void onComplete() {
         this.upstream = DisposableHelper.DISPOSED;
         this.downstream.onSuccess(false);
      }

      @Override
      public void onError(Throwable var1) {
         this.upstream = DisposableHelper.DISPOSED;
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
      public void onSuccess(Object var1) {
         this.upstream = DisposableHelper.DISPOSED;
         this.downstream.onSuccess(ObjectHelper.equals(var1, this.value));
      }
   }
}
