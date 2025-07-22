package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

public final class SingleDetach<T> extends Single<T> {
   final SingleSource<T> source;

   public SingleDetach(SingleSource<T> var1) {
      this.source = var1;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      this.source.subscribe(new SingleDetach.DetachSingleObserver<>(var1));
   }

   static final class DetachSingleObserver<T> implements SingleObserver<T>, Disposable {
      SingleObserver<? super T> downstream;
      Disposable upstream;

      DetachSingleObserver(SingleObserver<? super T> var1) {
         this.downstream = var1;
      }

      @Override
      public void dispose() {
         this.downstream = null;
         this.upstream.dispose();
         this.upstream = DisposableHelper.DISPOSED;
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
      }

      @Override
      public void onError(Throwable var1) {
         this.upstream = DisposableHelper.DISPOSED;
         SingleObserver var2 = this.downstream;
         if (var2 != null) {
            this.downstream = null;
            var2.onError(var1);
         }
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
         SingleObserver var2 = this.downstream;
         if (var2 != null) {
            this.downstream = null;
            var2.onSuccess(var1);
         }
      }
   }
}
