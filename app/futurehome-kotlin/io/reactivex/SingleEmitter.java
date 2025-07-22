package io.reactivex;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;

public interface SingleEmitter<T> {
   boolean isDisposed();

   void onError(Throwable var1);

   void onSuccess(T var1);

   void setCancellable(Cancellable var1);

   void setDisposable(Disposable var1);

   boolean tryOnError(Throwable var1);
}
