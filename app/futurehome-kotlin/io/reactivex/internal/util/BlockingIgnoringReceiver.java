package io.reactivex.internal.util;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.concurrent.CountDownLatch;

public final class BlockingIgnoringReceiver extends CountDownLatch implements Consumer<Throwable>, Action {
   public Throwable error;

   public BlockingIgnoringReceiver() {
      super(1);
   }

   public void accept(Throwable var1) {
      this.error = var1;
      this.countDown();
   }

   @Override
   public void run() {
      this.countDown();
   }
}
