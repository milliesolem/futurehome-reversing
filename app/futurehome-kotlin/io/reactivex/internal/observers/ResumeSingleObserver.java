package io.reactivex.internal.observers;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class ResumeSingleObserver<T> implements SingleObserver<T> {
   final SingleObserver<? super T> downstream;
   final AtomicReference<Disposable> parent;

   public ResumeSingleObserver(AtomicReference<Disposable> var1, SingleObserver<? super T> var2) {
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
   public void onSuccess(T var1) {
      this.downstream.onSuccess((T)var1);
   }
}
