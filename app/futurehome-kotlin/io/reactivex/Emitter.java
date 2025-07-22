package io.reactivex;

public interface Emitter<T> {
   void onComplete();

   void onError(Throwable var1);

   void onNext(T var1);
}
