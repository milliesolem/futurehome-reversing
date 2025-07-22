package io.reactivex;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;

public interface CompletableEmitter {
   boolean isDisposed();

   void onComplete();

   void onError(Throwable var1);

   void setCancellable(Cancellable var1);

   void setDisposable(Disposable var1);

   boolean tryOnError(Throwable var1);
}
