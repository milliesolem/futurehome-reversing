package io.reactivex;

import io.reactivex.disposables.Disposable;

public interface SingleObserver<T> {
   void onError(Throwable var1);

   void onSubscribe(Disposable var1);

   void onSuccess(T var1);
}
