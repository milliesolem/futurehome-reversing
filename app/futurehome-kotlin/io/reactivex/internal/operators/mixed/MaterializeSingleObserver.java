package io.reactivex.internal.operators.mixed;

import io.reactivex.CompletableObserver;
import io.reactivex.MaybeObserver;
import io.reactivex.Notification;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

public final class MaterializeSingleObserver<T> implements SingleObserver<T>, MaybeObserver<T>, CompletableObserver, Disposable {
   final SingleObserver<? super Notification<T>> downstream;
   Disposable upstream;

   public MaterializeSingleObserver(SingleObserver<? super Notification<T>> var1) {
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
      this.downstream.onSuccess(Notification.createOnComplete());
   }

   @Override
   public void onError(Throwable var1) {
      this.downstream.onSuccess(Notification.createOnError(var1));
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
      this.downstream.onSuccess(Notification.createOnNext((T)var1));
   }
}
