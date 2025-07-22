package io.reactivex.internal.subscribers;

public final class BlockingLastSubscriber<T> extends BlockingBaseSubscriber<T> {
   public void onError(Throwable var1) {
      this.value = null;
      this.error = var1;
      this.countDown();
   }

   public void onNext(T var1) {
      this.value = (T)var1;
   }
}
