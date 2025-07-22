package io.reactivex;

import io.reactivex.disposables.Disposable;

public interface MaybeObserver<T> {
   void onComplete();

   void onError(Throwable var1);

   void onSubscribe(Disposable var1);

   void onSuccess(T var1);
}
