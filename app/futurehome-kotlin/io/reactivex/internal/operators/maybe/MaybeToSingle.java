package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.HasUpstreamMaybeSource;
import java.util.NoSuchElementException;

public final class MaybeToSingle<T> extends Single<T> implements HasUpstreamMaybeSource<T> {
   final T defaultValue;
   final MaybeSource<T> source;

   public MaybeToSingle(MaybeSource<T> var1, T var2) {
      this.source = var1;
      this.defaultValue = (T)var2;
   }

   @Override
   public MaybeSource<T> source() {
      return this.source;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      this.source.subscribe(new MaybeToSingle.ToSingleMaybeSubscriber<>(var1, this.defaultValue));
   }

   static final class ToSingleMaybeSubscriber<T> implements MaybeObserver<T>, Disposable {
      final T defaultValue;
      final SingleObserver<? super T> downstream;
      Disposable upstream;

      ToSingleMaybeSubscriber(SingleObserver<? super T> var1, T var2) {
         this.downstream = var1;
         this.defaultValue = (T)var2;
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
         Object var1 = this.defaultValue;
         if (var1 != null) {
            this.downstream.onSuccess((T)var1);
         } else {
            this.downstream.onError(new NoSuchElementException("The MaybeSource is empty"));
         }
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
      public void onSuccess(T var1) {
         this.upstream = DisposableHelper.DISPOSED;
         this.downstream.onSuccess((T)var1);
      }
   }
}
