package io.reactivex.internal.observers;

public interface InnerQueuedObserverSupport<T> {
   void drain();

   void innerComplete(InnerQueuedObserver<T> var1);

   void innerError(InnerQueuedObserver<T> var1, Throwable var2);

   void innerNext(InnerQueuedObserver<T> var1, T var2);
}
