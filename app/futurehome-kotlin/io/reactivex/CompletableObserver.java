package io.reactivex;

import io.reactivex.disposables.Disposable;

public interface CompletableObserver {
   void onComplete();

   void onError(Throwable var1);

   void onSubscribe(Disposable var1);
}
