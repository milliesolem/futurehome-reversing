package io.reactivex;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;

public interface ObservableEmitter<T> extends Emitter<T> {
   boolean isDisposed();

   ObservableEmitter<T> serialize();

   void setCancellable(Cancellable var1);

   void setDisposable(Disposable var1);

   boolean tryOnError(Throwable var1);
}
