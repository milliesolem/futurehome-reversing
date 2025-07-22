package io.reactivex.internal.subscribers;

public interface InnerQueuedSubscriberSupport<T> {
   void drain();

   void innerComplete(InnerQueuedSubscriber<T> var1);

   void innerError(InnerQueuedSubscriber<T> var1, Throwable var2);

   void innerNext(InnerQueuedSubscriber<T> var1, T var2);
}
