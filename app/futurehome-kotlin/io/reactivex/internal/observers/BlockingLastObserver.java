package io.reactivex.internal.observers;

public final class BlockingLastObserver<T> extends BlockingBaseObserver<T> {
   @Override
   public void onError(Throwable var1) {
      this.value = null;
      this.error = var1;
      this.countDown();
   }

   @Override
   public void onNext(T var1) {
      this.value = (T)var1;
   }
}
