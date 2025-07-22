package io.reactivex;

import io.reactivex.disposables.Disposable;

public interface Observer<T> {
   void onComplete();

   void onError(Throwable var1);

   void onNext(T var1);

   void onSubscribe(Disposable var1);
}
