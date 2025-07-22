package io.reactivex.internal.observers;

public final class BlockingFirstObserver<T> extends BlockingBaseObserver<T> {
   @Override
   public void onError(Throwable var1) {
      if (this.value == null) {
         this.error = var1;
      }

      this.countDown();
   }

   @Override
   public void onNext(T var1) {
      if (this.value == null) {
         this.value = (T)var1;
         this.upstream.dispose();
         this.countDown();
      }
   }
}
