package io.reactivex;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;

public interface FlowableEmitter<T> extends Emitter<T> {
   boolean isCancelled();

   long requested();

   FlowableEmitter<T> serialize();

   void setCancellable(Cancellable var1);

   void setDisposable(Disposable var1);

   boolean tryOnError(Throwable var1);
}
