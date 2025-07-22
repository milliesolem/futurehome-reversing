package io.reactivex.internal.subscribers;

import io.reactivex.plugins.RxJavaPlugins;

public final class BlockingFirstSubscriber<T> extends BlockingBaseSubscriber<T> {
   public void onError(Throwable var1) {
      if (this.value == null) {
         this.error = var1;
      } else {
         RxJavaPlugins.onError(var1);
      }

      this.countDown();
   }

   public void onNext(T var1) {
      if (this.value == null) {
         this.value = (T)var1;
         this.upstream.cancel();
         this.countDown();
      }
   }
}
